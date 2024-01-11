import React, { useEffect, useState, useContext } from 'react';
import { useParams } from 'react-router-dom';
import { Table, ConfigProvider, Button } from 'antd';
import { ThemeContext } from '../../context/themeContext';

interface RjesenjeInfo {
        korisnickoIme: string;
        natjecanjeId: BigInt;
        rBr: BigInt;
        postotakTocnihPrimjera: number;
        zadtakId: BigInt;
}

interface ProblemResultsProps {
        zadatakId: number | undefined,
        natjecatelj: string,
}

const ProblemSolutions: React.FC<ProblemResultsProps> = ({ zadatakId, natjecatelj }) => {
        const { nadmetanjeId } = useParams();
        const [rjesenja, setRjesenja] = useState<RjesenjeInfo[]>([]);
        const { theme } = useContext(ThemeContext);

        useEffect(() => {
                const fetchData = async () => {
                        try {
                                const response = await fetch(`/api/solutions/get/competition/${nadmetanjeId}?zadatak=${zadatakId}&natjecatelj=${natjecatelj}`);
                                if (!response.ok) {
                                        throw new Error('Failed to fetch data');
                                }
                                const data: RjesenjeInfo[] = await response.json();
                                setRjesenja(data);
                        } catch (error) {
                                console.error('Error fetching data:', error);
                        }
                };

                fetchData();
        }, [nadmetanjeId, zadatakId, natjecatelj]);

        const downloadSolution = async (rbr: BigInt) => {
                try {
                        const response = await fetch(`/api/solutions/code?rbr=${rbr}&zadatak=${zadatakId}&natjecatelj=${natjecatelj}`);
                        if (!response.ok) {
                                throw new Error('Failed to fetch solution code');
                        }

                        const solutionCode = await response.text();

                        let formattedSolutionCode = solutionCode.replace(/\\n/g, '\n');
                        formattedSolutionCode = formattedSolutionCode.substring(1, formattedSolutionCode.length - 1);


                        const blob = new Blob([formattedSolutionCode], { type: 'text/plain' });

                        const link = document.createElement('a');
                        link.href = window.URL.createObjectURL(blob);
                        link.download = `solution_${rbr}.cpp`;
                        link.click();
                } catch (error) {
                        console.error('Error downloading solution code:', error);
                }
        };


        const columns = [
                {
                        title: 'Rješenje',
                        dataIndex: 'rBr',
                        key: 'rBr',
                },
                {
                        title: 'Rješenost (%)',
                        dataIndex: 'postotakTocnihPrimjera',
                        key: 'postotakTocnihPrimjera',
                },
                {
                        title: 'Preuzimanje',
                        key: 'actions',
                        render: (record: RjesenjeInfo) => (
                                <Button className='download' onClick={() => downloadSolution(record.rBr)}>
                                        <span>Dohvati</span>
                                </Button>
                        ),
                },
        ];

        return (
                <div className="tableWrapper">
                        <div className="info-table">
                                {
                                        theme.isThemeDark == false ? (
                                                <ConfigProvider
                                                        theme={{

                                                                components: {
                                                                        Table: {
                                                                                headerBg: "#f4c95de7",
                                                                                rowHoverBg: "#f4c95d52",
                                                                                borderColor: "#00000085",
                                                                                headerFilterHoverBg: "#f4c95de7",
                                                                                headerSortActiveBg: "#f4c95de7",
                                                                                headerSortHoverBg: "#f4c95da9",
                                                                                headerBorderRadius: 0,
                                                                                colorPrimary: "#dd7230",
                                                                                headerSplitColor: "transparent",
                                                                                colorBgContainer: "#fff",
                                                                                colorText: "#000"
                                                                        },
                                                                },
                                                        }}
                                                >
                                                        <Table pagination={false} dataSource={rjesenja} columns={columns} />
                                                </ConfigProvider>
                                        ) : (
                                                <ConfigProvider
                                                        theme={{
                                                                components: {
                                                                        Table: {
                                                                                headerBg: "#dd7230",
                                                                                rowHoverBg: "#dcdcdc34",
                                                                                borderColor: "#00000085",
                                                                                headerFilterHoverBg: "#dd7230e0",
                                                                                headerSortActiveBg: "#dd7230e0",
                                                                                headerSortHoverBg: "#dd7230",
                                                                                headerBorderRadius: 0,
                                                                                colorPrimary: "#f4c95d",
                                                                                headerSplitColor: "transparent",
                                                                                colorBgContainer: "#2A2D34",
                                                                                colorText: " #ECDCC9",
                                                                        },
                                                                },
                                                        }}
                                                >
                                                        <Table pagination={false} dataSource={rjesenja} columns={columns} />
                                                </ConfigProvider>
                                        )}
                        </div>
                </div>
        );
};

export default ProblemSolutions;
