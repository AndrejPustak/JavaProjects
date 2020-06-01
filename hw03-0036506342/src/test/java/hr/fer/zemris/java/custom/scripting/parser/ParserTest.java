package hr.fer.zemris.java.custom.scripting.parser;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.lexer.LexerException;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;

public class ParserTest {
	
	@Test 
	public void docBodyNull(){
		
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(null));
		
	}
	
	@Test
	public void docBodyEmpty() {
		SmartScriptParser parser = new SmartScriptParser("");
		
		DocumentNode document = new DocumentNode();
		
		assertEquals(true, document.equals(parser.getDocumentNode()));
	}
	
	@Test 
	public void oneWordDocument() {
		SmartScriptParser parser = new SmartScriptParser("test");
		
		DocumentNode document = new DocumentNode();
		document.addChildNode(new TextNode("test"));
		
		assertEquals(true, document.equals(parser.getDocumentNode()));
	}
	
	@Test 
	public void oneWordOneEchoTest() {
		SmartScriptParser parser = new SmartScriptParser("Example \\{$=1$}. Now actually write one {$=1$}");
		
		DocumentNode document = new DocumentNode();
		document.addChildNode(new TextNode("Example {$=1$}. Now actually write one "));
		Element echoElements[] = {new ElementConstantInteger(1)};
		document.addChildNode(new EchoNode(echoElements));
		
		assertEquals(true, document.equals(parser.getDocumentNode()));
	}
	
	@Test
	public void tooManyEndTagsTest() {
		String docBody = loader("doc2.txt");
		
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(docBody));
	}
	
	@Test
	public void unexpectedSymbolsInEchoTest() {
		String docBody = loader("doc3.txt");
		
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(docBody));
	}
	
	@Test
	public void tagNotClosedTest() {
		String docBody = loader("doc4.txt");
		
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(docBody));
	}
	
	@Test
	public void stringInForTest() {
		String docBody = loader("doc5.txt");
		
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(docBody));
	}
	
	@Test
	public void functionInForTest() {
		String docBody = loader("doc6.txt");
		
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(docBody));
	}
	
	@Test
	public void unexpectedElementsInEndTagTest() {
		String docBody = loader("doc7.txt");
		
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(docBody));
	}
	
	
	
	private String loader(String filename) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try(InputStream is =
		this.getClass().getClassLoader().getResourceAsStream(filename)) {
		byte[] buffer = new byte[1024];
		while(true) {
		int read = is.read(buffer);
		if(read<1) break;
		bos.write(buffer, 0, read);
		}
		return new String(bos.toByteArray(), StandardCharsets.UTF_8);
		} catch(IOException ex) {
		return null;
		}
		}
}
