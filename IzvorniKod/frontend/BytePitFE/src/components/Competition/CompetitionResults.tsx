import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import LoadingOverlay from 'react-loading-overlay-nextgen';

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

const CompetitionResults: React.FC = () => {
        const { nadmetanjeId } = useParams<{ nadmetanjeId: string }>();
        const [loading, setLoading] = useState(true);
        const [rankResults, setRankResults] = useState<RangDTO[]>([]);
        const [taskDetails, setTaskDetails] = useState<Record<number, ProblemDetails>>({});

        useEffect(() => {
                const fetchData = async () => {
                        try {
                                // čekanje da se rezultati na backendu poslože
                                setTimeout(async () => {
                                        setLoading(false);

                                        const response = await fetch(`/api/natjecanja/get/rang/${nadmetanjeId}`);
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
                return (seconds / 3600).toString() + "h "
                        + (seconds % 3600 / 60).toString() + "min "
                        + (seconds % 60).toString() + "s";
        };

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
                                                                {rankResults.map((result, index) => (
                                                                        <tr key={index}>
                                                                                <td>{result.rang}</td>
                                                                                <td>{result.username}</td>
                                                                                <td>{formatDuration(result.vrijemeRjesavanja)}</td>
                                                                                {Object.keys(result.zadatakBodovi).map((taskId) => (
                                                                                        <td key={taskId}>{result.zadatakBodovi[parseInt(taskId)]}</td>
                                                                                ))}
                                                                                <td>{result.ukupniBodovi}</td>
                                                                        </tr>
                                                                ))}
                                                        </tbody>
                                                </table>
                                        </div>
                                </div>
                        </LoadingOverlay>
                </div>
        );
};

export default CompetitionResults;
