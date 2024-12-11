package sg.edu.nus.iss.vttp5a_ssf_day19ws_plus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import sg.edu.nus.iss.vttp5a_ssf_day19ws_plus.service.FileReaderService;

@SpringBootApplication
public class Vttp5aSsfDay19wsPlusApplication implements CommandLineRunner{

	@Autowired
	FileReaderService fileReaderService;

	public static void main(String[] args) {
		SpringApplication.run(Vttp5aSsfDay19wsPlusApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		fileReaderService.loadFileToRedis();
	}

}
