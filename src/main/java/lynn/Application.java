package lynn;

import java.io.File;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.kernel.impl.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.core.GraphDatabase;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import lynn.models.Cell;
import lynn.models.Host;
import lynn.repos.CellRepository;
import lynn.repos.HostRepository;

@SpringBootApplication
public class Application implements CommandLineRunner {

	@Configuration
	@EnableTransactionManagement
	@EnableNeo4jRepositories(basePackages = "lynn")
	static class ApplicationConfig extends Neo4jConfiguration {

		public ApplicationConfig() {
			setBasePackage("lynn");
		}

		@Bean
		GraphDatabaseService graphDatabaseService() {
			return new GraphDatabaseFactory().newEmbeddedDatabase("accessingdataneo4j.db");
		}
	}

	@Autowired CellRepository cellRepository;
	@Autowired HostRepository hostRepository;
	@Autowired GraphDatabase graphDatabase;

	public void run(String... args) throws Exception {
		// Just temporary as we test out the db...
		Host lynnAI = new Host("LynnAI");
		Cell emily = new Cell("Emily", "Cell City Culturer");
		Cell julia = new Cell("Julia", "Endoplasmic RIDICULUM");
		Cell noah = new Cell("Noah", "*insert witty about section here*");
		
		System.out.println("Before linking up with Neo4j...");
		for (Cell cell : new Cell[] { emily, julia, noah }) {
			System.out.println(cell);
		}
		
		Transaction tx = graphDatabase.beginTx();
		try {
			hostRepository.save(lynnAI);
			cellRepository.save(emily);
			cellRepository.save(julia);
			cellRepository.save(noah);
			
			lynnAI = hostRepository.findByName(lynnAI.getName());
			lynnAI.addCreatedCell(emily);
			lynnAI.addCreatedCell(julia);
			lynnAI.addCreatedCell(noah);
			hostRepository.save(lynnAI);
			
			emily = cellRepository.findByName(emily.getName());
			emily.addToCytoplasm(julia);
			cellRepository.save(emily);

			julia = cellRepository.findByName(julia.getName());
			julia.addToCytoplasm(noah);
			cellRepository.save(julia);

			System.out.println("Lookup each cell by name...");
			for (String name : new String[] { emily.getName(), julia.getName(), noah.getName() }) {
				System.out.println(cellRepository.findByName(name));
			}

			System.out.println("Looking up Emily's Cytoplasm...");
			for (Cell cell : cellRepository.findCytoplasmByCellName(emily.getName())) {
				System.out.println(cell.getName() + " shares a cytoplasm with Emily.");
			}
			
			System.out.println("Looking up Emily's host...");
			System.out.println(emily.getHost());
			
			System.out.println("Looking up LynnAI...");
			System.out.println(hostRepository.findByName(lynnAI.getName()));
			
			System.out.println("Looking up LynnAI's created cells...");
			for (Cell cell : cellRepository.findCellsByHostName(lynnAI.getName())) {
				System.out.println(cell.getName() + " was created by LynnAI");
			}

			tx.success();
		} finally {
			tx.close();
		}
	}

	public static void main(String[] args) throws Exception {
		System.out.println("Deleting previous db from memory...");
		FileUtils.deleteRecursively(new File("accessingdataneo4j.db"));

		SpringApplication.run(Application.class, args);
	}
	
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		  registry.addResourceHandler("/assets/**")
		    .addResourceLocations("classpath:/assets/");
		  registry.addResourceHandler("/css/**")
		    .addResourceLocations("/css/");
		  registry.addResourceHandler("/img/**")
		    .addResourceLocations("/img/");
		  registry.addResourceHandler("/js/**")
		    .addResourceLocations("/js/");
		}

}
