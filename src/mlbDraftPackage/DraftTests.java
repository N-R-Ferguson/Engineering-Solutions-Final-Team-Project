package mlbDraftPackage;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class DraftTests {
	private LeagueMembers testArray [], testArray2[], testArray3[];
	private ArrayList<PlayerData> testDraftDatabase;
	private SaveRestore sr;
	private DraftCommands d;
	@Before
	public void setUp() throws Exception {
		testArray= new LeagueMembers[4];
		testArray2= new LeagueMembers[4];
		testDraftDatabase = new ArrayList<PlayerData>();
		testArray[0] = new LeagueMembers("A", 13);
		testArray[1] = new LeagueMembers("B", 13);
		testArray[2] = new LeagueMembers("C", 13);
		testArray[3] = new LeagueMembers("D", 13);
		testArray[0].addPlayer(new NonPitchers("NickCastellanos", "RF", 0.545, 0.583, 11, 1.364, 0));
		testArray[1].addPlayer(new NonPitchers("BillyBob", "RF", 0.545, 0.583, 11, 1.364, 0));
		testArray[2].addPlayer(new NonPitchers("JoeCastanza", "RF", 0.545, 0.583, 11, 1.364, 0));
		testArray[3].addPlayer(new NonPitchers("FlyinHigh", "RF", 0.545, 0.583, 11, 1.364, 0));
		testArray[3].addPlayer(new Pitchers("PitcherMan", "P", 0.545, 0.583, 11, 1.364, 0));
		testArray2[0] = new LeagueMembers("A", 13);
		testArray2[1] = new LeagueMembers("B", 13);
		testArray2[2] = new LeagueMembers("C", 13);
		testArray2[3] = new LeagueMembers("D", 13);
		sr = new SaveRestore();
		d = new DraftCommands();
		d.pullMLBData();
		
	}

	//Based on World Series data
	
	//ODRAFT tests here
	@Test
	public void testPullMLBData() {
		assertEquals(196, d.getDraftDatabase().size());
	}
	
	@Test
	public void testODRAFT() {
		d.odraft("MiguelC", "A");
		testArray3 = d.getLeagueMembers();
		assertEquals(1, testArray3[0].getTeamDatabase().getLength());
	}
	
	//IDRAFT tests here
	@Test
	public void testIDRAFT() {
		d.odraft("MiguelC", "A");
		testArray3 = d.getLeagueMembers();
		assertEquals(1, testArray3[0].getTeamDatabase().getLength());
	}
	
	//Data Storage retrieval tests
	@Test
	public void testGetAVG() {
		PlayerData testPlayer = testArray[0].getTeamDatabase().getPlayerData(0);
		assertTrue(0.545 == ((NonPitchers)testPlayer).getAVG());
	}
	
	public void testGetPlayerName() {
		PlayerData testPlayer = testArray[0].getTeamDatabase().getPlayerData(0);
		assertTrue( "NickCastellanos".contentEquals(testPlayer.getPlayerName()));
	}
	
	//TEAM tests here
	@Test
	public void testTEAM() {
//		assertEquals("team for A" , DraftCommands.team("A"), "C Chirinos,R \n1B Zimmerman,R \n2B Altuve,J \n3B Rendon,A \nSS Turner,T \nLF "
//				+ "Diaz,A \nCF Springer,G \nRF Eaton,A \nP1 Cole,G \nP2 Corbin,P \nP3 Doolittle,S \nP4 James,J \nP5 Ross,J \n");
	}
	
	//STARS tests here
	@Test
	public void testSTARS() {
//		assertEquals("stars for A" , DraftCommands.stars("A"), "RF Eaton,A \nC Chirinos,R \nP1 Cole,G \nP2 Corbin,P \nCF Springer,G \nLF Diaz,A "
//				+ "\nP3 Doolittle,S \n1B Zimmerman,R \nP4 James,J \n2B Altuve,J \nSS Turner,T \n P5 Ross,J \n3B Rendon,A \n");
	}
	
	//EVALFUN tests here
	@Test
	public void testEVALFUN() {
		//AVG, OBP, AB, SLG, SB
		d.evalfun("1.05 * avg / sb");
		assertEquals("evalfun" , d.getExpressions("e"), "1.05 * avg / sb");
		d.evalfun("1.05 * g / sb");
		assertEquals("evalfun" , d.getExpressions("e"), "");

	}
	
	//PEVALFUN tests here
	@Test
	public void testPEVALFUN() {
		//G, GS, ERA, IP, BB
		d.pevalfun("1.05 * g / bb");
		assertEquals("pevalfun" , d.getExpressions("p"), "1.05 * g / bb");
		d.pevalfun("1.05 * a / ip");
		assertEquals("pevalfun" , d.getExpressions("p"), "");

	}
	

	@Test
	public void testSave() {
		sr.save(testArray, testDraftDatabase, "filename.fantasy.txt");
		assertEquals(true, testArray[0].getDataSaved());
	}
	@Test
	public void testSaveWhenThereIsNull() {
		sr.save(testArray2, testDraftDatabase, "filename2.fantasy.txt");
		assertEquals(true, testArray2[0].getDataSaved());
	}
	
	@Test
	public void testRestore() {
		sr.restore("filename.fantasy.txt");
		
		testArray2 = sr.getMembers();
		testDraftDatabase = new ArrayList<PlayerData>(sr.getDraftDatabase());
		
		assertEquals(1, testArray2[0].getTeamDatabase().getLength());
		assertEquals(0, testDraftDatabase.size());
	}
	@Test
	public void testRestoreAllNull() {
		sr.restore("filename2.fantasy.txt");
		
		testArray2 = sr.getMembers();
		testDraftDatabase = new ArrayList<PlayerData>(sr.getDraftDatabase());
		
		assertEquals(0, testArray2[0].getTeamDatabase().getLength());
		assertEquals(0, testDraftDatabase.size());
	}
}
