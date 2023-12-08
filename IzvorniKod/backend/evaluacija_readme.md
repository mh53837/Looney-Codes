primjer json za poziv upload rute:
{
    "zadatakId" : 1005,
    "korisnickoIme" : "spiderman",
    "programskiKod" : "#include<bits/stdc++.h>\n using namespace std;\n int main(){string s;\n cin >> s;\n cout << s;}"
}


***JUDGE0 API ukratko:***
detaljnije: 	https://rapidapi.com/judge0-official/api/judge0-ce
		https://ce.judge0.com/		

CREATE SUBMISSION:

	- submission je zapravo testiranje na jednom test primjeru

	primjer json-a u zahtjeva za stvaranje:

	{
	  "source_code": "#include <stdio.h>\n\nint main() { printf(\"Hello, World!\"); return 0; }",
	  "language_id": 52,
	  "stdin": "",
	  "expected_output": "Hello, World!"
	}

	- source_kod - očito
	- language_id - 52 = C++ (ostale moreš videti na https://ce.judge0.com/languages/ - ovi s niskim brojevima više ne delaju treba najti 40+ broja za pojedini jezik)
	- stdin - ulazni podaci
	- expected_output - očekivani izlazni podaci
	- cpu_time_limit - time_limit, defaultno dve sekunde
	- detaljna tablica s parametrima: https://ce.judge0.com/#submissions
	
	
	response: u obliku json-a, ako je sve u redu s requestom vrača token kao npr
		
		{ "token" : "23e0a9c2-df51-4b41-8412-6cf1a66520e4"}
		
	
	
	
	
	
				
GET SUBMISSION

	- dohvaćanje rezultate izvršavanja, uključujući izlaz programa, status izvršavanja (https://ce.judge0.com/statuses) i druge informacije

	- sve tekstualno je u base64 encodingu!


	primjer dobivenog json-a za GET submission rutu iz API-ja:
	{
	    "source_code": "I2luY2x1ZGU8Yml0cy9zdGRjKysuaD4KIHVzaW5nIG5hbWVzcGFjZSBzdGQ7\nCiBpbnQgbWFpbigpIHtzdHJpbmcgczsKIGNpbiA+PiBzOwogY291dCA8PCBz\nO30=\n",
	    "language_id": 52,
	    "stdin": "amVrYQ==\n",
	    "expected_output": "amVrYQ==\n",
	    "stdout": "amVrYQ==\n",
	    "status_id": 3,
	    "created_at": "2023-12-08T15:37:06.017Z",
	    "finished_at": "2023-12-08T15:37:07.323Z",
	    "time": "0.002",
	    "memory": 752,
	    "stderr": null,
	    "token": "23e0a9c2-df51-4b41-8412-6cf1a66520e4",
	    "number_of_runs": 1,
	    "cpu_time_limit": "5.0",
	    "cpu_extra_time": "1.0",
	    "wall_time_limit": "10.0",
	    "memory_limit": 128000,
	    "stack_limit": 64000,
	    "max_processes_and_or_threads": 60,
	    "enable_per_process_and_thread_time_limit": false,
	    "enable_per_process_and_thread_memory_limit": false,
	    "max_file_size": 1024,
	    "compile_output": null,
	    "exit_code": 0,
	    "exit_signal": null,
	    "message": null,
	    "wall_time": "0.043",
	    "compiler_options": null,
	    "command_line_arguments": null,
	    "redirect_stderr_to_stdout": false,
	    "callback_url": null,
	    "additional_files": null,
	    "enable_network": false,
	    "status": {
		"id": 3,
		"description": "Accepted"
	    },
	    "language": {
		"id": 52,
		"name": "C++ (GCC 7.4.0)"
	    }
	}
