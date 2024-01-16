import React, { useContext, useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import LoadingOverlay from "react-loading-overlay-nextgen";
import { Modal, ConfigProvider } from "antd";
import { ThemeContext } from "../../context/themeContext";
import ProblemSolutions from "../Problems/ProblemSolutions";
import { UserContext } from "../../context/userContext";

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
  virtualno: boolean;
}

/* const ProblemSolutions = React.lazy(
  () => import("../Problems/ProblemSolutions")
); */

const CompetitionResults: React.FC<CompetitionProps> = ({ virtualno }) => {
  const { nadmetanjeId } = useParams<{ nadmetanjeId: string }>();
  const [loading, setLoading] = useState(true);
  const [rankResults, setRankResults] = useState<RangDTO[]>([]);
  const [taskDetails, setTaskDetails] = useState<Record<number, ProblemDetails>>({});
  const [selectedTask, setSelectedTask] = useState<number | undefined>(); // Added state for selected task
  const [competitor, setCompetitor] = useState<string>("");
  const [open, setOpen] = useState<boolean>(false);

	const { theme } = useContext(ThemeContext);
	const { user } = useContext(UserContext);
	useEffect(() => {
	}, [theme.isThemeDark]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        // čekanje da se rezultati na backendu poslože
        setTimeout(async () => {
          setLoading(false);

          const response = virtualno
            ? await fetch(`/api/virtualnaNatjecanja/get/rang/${nadmetanjeId}`)
            : await fetch(`/api/natjecanja/get/rang/${nadmetanjeId}`);
          if (!response.ok) {
            throw new Error("Failed to fetch data");
          }

          const data: RangDTO[] = await response.json();
          setRankResults(data);

          const tasksPromises = Object.keys(data[0]?.zadatakBodovi || {}).map(
            (taskId) => {
              return fetch(`/api/problems/get/${taskId}`).then((res) =>
                res.json()
              );
            }
          );

          const tasksDetails: ProblemDetails[] = await Promise.all(
            tasksPromises
          );

          const detailsMapping: Record<number, ProblemDetails> = {};
          tasksDetails.forEach((taskDetail) => {
            detailsMapping[taskDetail.zadatakId] = taskDetail;
          });

          setTaskDetails(detailsMapping);
        }, 3000);
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    };

    fetchData();
  }, [nadmetanjeId]);

  // formatiranje ispisa vremena
  const formatDuration = (seconds: number) => {
    return (
      Math.floor(seconds / 3600).toString() +
      "h " +
      Math.floor((seconds % 3600) / 60).toString() +
      "min " +
      (seconds % 60).toString() +
      "s"
    );
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
  };

	const renderModal = () => {
		return (
			<Modal open={open} onCancel={handleClose} footer={null}>
			<div>
				<React.Suspense fallback={<div>učitavanje...</div>}>
					<br />
				{user.uloga != "NATJECATELJ" && <h4 style={{padding:5}}>Preuzimanje rješenja zadataka omogućeno je samo natjecateljima koji su prethodno točno riješili taj zadatak.</h4>}
					<br />
					<ProblemSolutions
						zadatakId={selectedTask}
						natjecatelj={competitor}
					/>
				</React.Suspense>
			</div>
		</Modal>
		);
	}

  return (
    <div>
      <LoadingOverlay active={loading} spinner text="Učitavanje...">
        <div>
          <h2>Rezultati</h2>
          <div className="info-table">
            <table>
              <thead>
                <tr>
                  <th>Rank</th>
                  <th>Natjecatelj</th>
                  <th>Vrijeme rješavanja</th>
                  {Object.keys(
                    rankResults.length > 0 ? rankResults[0].zadatakBodovi : {}
                  ).map((taskId) => (
                    <th key={taskId}>
                      {taskDetails[parseInt(taskId, 10)]?.nazivZadatka ||
                        `Task ${taskId}`}
                    </th>
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
                      <td key={taskId}>
                        <button
                          onClick={() =>
                            openResult(parseInt(taskId), result.username)
                          }
                        >
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
				theme.isThemeDark === false ? (
					<>
						<ConfigProvider
							theme={{
								components: {
									Modal: {
										contentBg: "#fff",
									},
									Button: {
										colorPrimary: "#dd7230",
										colorPrimaryHover: "#dd723081",
										colorPrimaryActive: "#dd723081",
									},
								},
							}}
						>
							{renderModal()}
						</ConfigProvider>
					</>
				) : (
					<>
						<ConfigProvider
							theme={{
								components: {
									Modal: {
										contentBg: "#2A2D34",
										headerBg: "#2A2D34",
										footerBg: "#2A2D34",
										titleColor: "#ECDCC9",
										colorPrimary: "#ECDCC9",
										colorText: "#ECDCC9",
									},
									Button: {
										colorPrimary: "#dd7230",
										colorPrimaryHover: "#dd723081",
										colorPrimaryActive: "#dd723081",
										textHoverBg: "#2A2D34",
									},
								},
							}}
						>
							{renderModal()}
						</ConfigProvider>
					</>
				)
      )}
    </div>
  );
};

export default CompetitionResults;
