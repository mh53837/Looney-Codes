import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import LoadingOverlay from 'react-loading-overlay-nextgen';
import { Modal } from 'antd';

interface RangDTO {
        username: string;
        ukupniBodovi: number;
        rang: number;
        zadatakBodovi: Record<number, number>;
        vrijemeRjesavanja: number;
}

interface ProblemDetails {
        zadatakId: number;
        voditelj: string;
        natjecanje: string;
        nazivZadatka: string;
        brojBodova: number;
        vremenskoOgranicenje: number;
        tekstZadatka: string;
        privatniZadatak: boolean;
}

interface CompetitionProps {
        virtualno: boolean
}

const ProblemSolutions = React.lazy(() => import("../Problems/ProblemSolutions"));

const CompetitionResults: React.FC<CompetitionProps> = ({ virtualno }) => {
        const { nadmetanjeId } = useParams<{ nadmetanjeId: string }>();
        const [loading, setLoading] = useState(true);
        const [rankResults, setRankResults] = useState<RangDTO[]>([]);
        const [taskDetails, setTaskDetails] = useState<Record<number, ProblemDetails>>({});
        const [selectedTask, setSelectedTask] = useState<number | undefined>(); // Added state for selected task
        const [competitor, setCompetitor] = useState<string>("");
        const [open, setOpen] = useState<boolean>(false);

        const [virtualCompetitionResults, setVirtualCompetitionResults] = useState<RangDTO[]>([]);

        useEffect(() => {
                const fetchData = async () => {
                        try {
                                // čekanje da se rezultati na backendu poslože
                                setTimeout(async () => {
                                        setLoading(false);

                                        const response = virtualno ?
                                                await fetch(`/api/virtualnaNatjecanja/get/rang/${nadmetanjeId}`) :
                                                await fetch(`/api/natjecanja/get/rang/${nadmetanjeId}`);
                                        if (!response.ok) {
                                                throw new Error('Failed to fetch data');
                                        }

                                        const data: RangDTO[] = await response.json();
                                        setRankResults(data);

                                        const tasksPromises = Object.keys(data[0]?.zadatakBodovi || {}).map((taskId) => {
                                                return fetch(`/api/problems/get/${taskId}`).then((res) => res.json());
                                        });

                                        const tasksDetails: ProblemDetails[] = await Promise.all(tasksPromises);

                                        const detailsMapping: Record<number, ProblemDetails> = {};
                                        tasksDetails.forEach((taskDetail) => {
                                                detailsMapping[taskDetail.zadatakId] = taskDetail;
                                        });

                                        const virtualResponse = await fetch(`/api/natjecanja/get/rang/${nadmetanjeId}`);
                                        if (virtualResponse.ok) {
                                                const virtualData: RangDTO[] = await virtualResponse.json();
                                                setVirtualCompetitionResults(virtualData);
                                        }

                                        setTaskDetails(detailsMapping);
                                }, 3000);
                        } catch (error) {
                                console.error('Error fetching data:', error);
                        }
                };

                fetchData();
        }, [nadmetanjeId]);

        // formatiranje ispisa vremena
        const formatDuration = (seconds: number) => {
                return Math.floor(seconds / 3600).toString() + "h "
                        + Math.floor(seconds % 3600 / 60).toString() + "min "
                        + (seconds % 60).toString() + "s";
        };

        const openResult = (zadatakId: number, korisnickoIme: string) => {
                setSelectedTask(zadatakId); // Set the selected task
                setCompetitor(korisnickoIme);
                setOpen(true);

        };

        const handleClose = () => {
                setOpen(false);
                setCompetitor("");
                setSelectedTask(-1);
        }




        return (
                <div>
                        <LoadingOverlay active={loading} spinner text="Loading...">
                                <div>
                                        <h2>Rezultati</h2>
                                        <div className="info-table">
                                                <table>
                                                        <thead>
                                                                <tr>
                                                                        <th>Rank</th>
                                                                        <th>Natjecatelj</th>
                                                                        <th>Vrijeme rješavanja</th>
                                                                        {Object.keys(rankResults.length > 0 ? rankResults[0].zadatakBodovi : {}).map((taskId) => (
                                                                                <th key={taskId}>{taskDetails[parseInt(taskId, 10)]?.nazivZadatka || `Task ${taskId}`}</th>
                                                                        ))}
                                                                        <th>Ukupno</th>
                                                                </tr>
                                                        </thead>
                                                        <tbody>
                                                        {[...rankResults, ...virtualCompetitionResults].map((result, index) => (
                                                            <tr key={index}>
                                                                    <td>{result.rang}</td>
                                                                    <td>{result.username}</td>
                                                                    <td>{formatDuration(result.vrijemeRjesavanja)}</td>
                                                                    {Object.keys(result.zadatakBodovi).map((taskId) => (
                                                                        <td key={taskId}>
                                                                                <button onClick={() => openResult(parseInt(taskId), result.username)}>
                                                                                        {result.zadatakBodovi[parseInt(taskId)]}
                                                                                </button>
                                                                        </td>
                                                                    ))}
                                                                    <td>{result.ukupniBodovi}</td>
                                                            </tr>
                                                        ))}
                                                        </tbody>
                                                </table>
                                        </div>
                                </div>
                        </LoadingOverlay>
                        {selectedTask !== undefined && (

                                <Modal
                                        open={open}
                                        onCancel={handleClose}
                                        footer={null}
                                >
                                        <div>
                                                <React.Suspense fallback={<div>učitavanje...</div>}>
                                                        <br />
                                                        <ProblemSolutions zadatakId={selectedTask} natjecatelj={competitor} />
                                                </React.Suspense>
                                        </div>
                                </Modal>

                        )}
                </div >
        );
};

export default CompetitionResults;
