package com;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;

public class MainTest {
	public static void main(String[] args) throws Exception {
		new MainTest().docToHtml();
	}

	// doc转换为html
	void docToHtml() throws Exception {
		String sourceFileName = "C:\\doc\\test.doc";
		String targetFileName = "C:\\doc\\test.html";
		String imagePathStr = "C:\\doc\\image\\";
		HWPFDocument wordDocument = new HWPFDocument(new FileInputStream(sourceFileName));
		org.w3c.dom.Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(document);
		// 保存图片，并返回图片的相对路径
		wordToHtmlConverter.setPicturesManager((content, pictureType, name, width, height) -> {
			try (FileOutputStream out = new FileOutputStream(imagePathStr + name)) {
				out.write(content);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "image/" + name;
		});
		wordToHtmlConverter.processDocument(wordDocument);
		org.w3c.dom.Document htmlDocument = wordToHtmlConverter.getDocument();
		DOMSource domSource = new DOMSource(htmlDocument);
		StreamResult streamResult = new StreamResult(new File(targetFileName));

		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer serializer = tf.newTransformer();
		serializer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
		serializer.setOutputProperty(OutputKeys.INDENT, "yes");
		serializer.setOutputProperty(OutputKeys.METHOD, "html");
		serializer.transform(domSource, streamResult);
	}
}
