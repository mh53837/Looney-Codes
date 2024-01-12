
export const fetchData = async (url: string, userCredentials: { korisnickoIme: string; lozinka: string }) => {
  try {
    const credentials = btoa(`${userCredentials.korisnickoIme}:${userCredentials.lozinka}`);
    const options = {
      method: "GET",
      headers: {
        Authorization: `Basic ${credentials}`,
        "Content-Type": "application/json",
      },
    };
    const response = await fetch(url, options);

    if (!response.ok) {
      throw new Error(`HTTP error! Status: ${response.status}`);
    }
    const data = await response.json();
    return data;
  } catch (error) {
    console.error("Error fetching data:", error);
  }
};