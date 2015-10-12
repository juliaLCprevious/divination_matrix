package divination.matrix;

import java.io.File;

import org.apache.commons.math3.random.RandomDataGenerator;
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

import divination.matrix.models.Hexagram;
import divination.matrix.models.Reading;
import divination.matrix.repos.HexagramRepository;
import divination.matrix.repos.ReadingRepository;

@SpringBootApplication
public class Application implements CommandLineRunner {

	@Configuration
	@EnableTransactionManagement
	@EnableNeo4jRepositories(basePackages = "divination.matrix")
	static class ApplicationConfig extends Neo4jConfiguration {

		public ApplicationConfig() {
			setBasePackage("divination.matrix");
		}

		@Bean(destroyMethod = "shutdown")
		GraphDatabaseService graphDatabaseService() {
			return new GraphDatabaseFactory().newEmbeddedDatabase("accessingdataneo4j.db");
		}
	}

	@Autowired HexagramRepository hexagramRepository;
	@Autowired ReadingRepository readingRepository;
	@Autowired GraphDatabase graphDatabase;
	
	public void run(String... args) throws Exception {
			
		Transaction tx = graphDatabase.beginTx();
		try {
			System.out.println("Creating hexagram data...");
			Hexagram[] hexagrams = {
				new Hexagram(1, "Force", "乾", "䷀"),
				new Hexagram(2, "Field", "坤", "䷁"),
				new Hexagram(3, "Sprouting", "屯", "䷂"),
				new Hexagram(4, "Enveloping", "蒙", "䷃"),
				new Hexagram(5, "Attending", "需", "䷄"),
				new Hexagram(6, "Arguing", "訟", "䷅"),
				new Hexagram(7, "Leading", "師", "䷆"),
				new Hexagram(8, "Grouping", "比", "䷇"),
				new Hexagram(9, "Small Accumulating", "小畜 ", "䷈"),
				new Hexagram(10, "Treading", "履", "䷉"),
				new Hexagram(11, "Pervading", "泰", "䷊"),
				new Hexagram(12, "Obstruction", "否", "䷋"),
				new Hexagram(13, "Concording People", "同人", "䷌"),
				new Hexagram(14, "Great Possessing", "大有", "䷍"),
				new Hexagram(15, "Humbling", "謙", "䷎"),
				new Hexagram(16, "Providing-For", "豫", "䷏"),
				new Hexagram(17, "Following", "隨", "䷐"),
				new Hexagram(18, "Correcting", "蠱", "䷑"),
				new Hexagram(19, "Nearing", "臨", "䷒"),
				new Hexagram(20, "Viewing", "觀", "䷓"),
				new Hexagram(21, "Gnawing Bite", "噬嗑", "䷔"),
				new Hexagram(22, "Adorning", "賁", "䷕"),
				new Hexagram(23, "Stripping", "剝", "䷖"),
				new Hexagram(24, "Returning", "復", "䷗"),
				new Hexagram(25, "Without Embroiling", "無妄", "䷘"),
				new Hexagram(26, "Great Accumulating", "大畜", "䷙"),
				new Hexagram(27, "Swallowing", "頤 ", "䷚"),
				new Hexagram(28, "Great Exceeding", "大過", "䷛"),
				new Hexagram(29, "Gorge", "坎", "䷜"),
				new Hexagram(30, "Radiance", "離", "䷝"),
				new Hexagram(31, "Conjoining", "咸", "䷞"),
				new Hexagram(32, "Persevering", "恆", "䷟"),
				new Hexagram(33, "Retiring", "遯", "䷠"),
				new Hexagram(34, "Great Invigorating", "大壯", "䷡"),
				new Hexagram(35, "Prospering", "晉", "䷢"),
				new Hexagram(36, "Darkening of the Light", "明夷", "䷣"),
				new Hexagram(37, "Dwelling People", "家人", "䷤"),
				new Hexagram(38, "Polarising", "睽", "䷥"),
				new Hexagram(39, "Limping", "蹇", "䷦"),
				new Hexagram(40, "Taking-Apart", "解", "䷧"),
				new Hexagram(41, "Diminishing", "損", "䷨"),
				new Hexagram(42, "Augmenting", "損", "䷩"),
				new Hexagram(43, "Displacement", "夬", "䷪"),
				new Hexagram(44, "Coupling", "姤", "䷫"),
				new Hexagram(45, "Clustering", "萃", "䷬"),
				new Hexagram(46, "Ascending", "升", "䷭"),
				new Hexagram(47, "Confining", "困", "䷮"),
				new Hexagram(48, "Welling", "井", "䷯"),
				new Hexagram(49, "Skinning", "革", "䷰"),
				new Hexagram(50, "Holding", "鼎", "䷱"),
				new Hexagram(51, "Shake", "震", "䷲"),
				new Hexagram(52, "Bound", "艮", "䷳"),
				new Hexagram(53, "Infiltrating", "漸", "䷴"),
				new Hexagram(54, "Converting the Maiden", "歸妹", "䷵"),
				new Hexagram(55, "Abounding", "豐", "䷶"),
				new Hexagram(56, "Sojourning", "旅", "䷷"),
				new Hexagram(57, "Ground", "巽", "䷸"),
				new Hexagram(58, "Open", "兌", "䷹"),
				new Hexagram(59, "Dispersing", "渙", "䷺"),
				new Hexagram(60, "Articulating", "節", "䷻"),
				new Hexagram(61, "Center Returning", "中孚", "䷼"),
				new Hexagram(62, "Small Exceeding", "小過", "䷽"),
				new Hexagram(63, "Already Fording", "既濟", "䷾"),
				new Hexagram(64, "Not Yet Fording", "未濟", "䷿"),
			};
			for (Hexagram hexagram : hexagrams) {
				hexagramRepository.save(hexagram);
				System.out.println(hexagram);
			}
			
			System.out.println("Creating reading data...");
			Reading[] readings = {
				new Reading("Noah", 0),
				new Reading("Emily", 0),
				new Reading("Julia", 0)
			};
			RandomDataGenerator r = new RandomDataGenerator();
			for (Reading reading : readings) {
				reading = readingRepository.save(reading);
				if (reading == null) {
					System.out.println("Something fucked up! Fix it, nerd!");
				}
				reading.setHexagram(hexagrams[r.nextInt(1, 64)]);
				readingRepository.save(reading);
				System.out.println(reading);
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
