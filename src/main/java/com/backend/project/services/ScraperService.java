package com.backend.project.services;

import com.backend.project.dto.FilesDto;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlStrong;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class ScraperService {

    private final static String baseUrl = "https://github.com";

    public List<FilesDto> getHtml(String url, List<FilesDto> filesDtoList) {
        WebClient client = new WebClient();
        client.getOptions().setJavaScriptEnabled(false);
        client.getOptions().setCssEnabled(false);
        client.getOptions().setUseInsecureSSL(true);

        try {
            HtmlPage htmlPage = client.getPage(url);
            List<HtmlElement> directoryList = htmlPage.getByXPath("//a[@class='js-navigation-open Link--primary']");

            //check if is a directory or a file
            if (directoryList.isEmpty()) {
                HtmlStrong htmlStrong = (HtmlStrong) htmlPage.getFirstByXPath("//strong[@class='final-path']");
                if (htmlStrong != null) {
                    String extension = getExtension((htmlStrong).asText());
                    Integer lines = htmlPage.getByXPath("//tr").size();
                    Long bytes = getBytes(htmlPage);
                    addFileInList(extension, lines, bytes.toString(), filesDtoList);
                }
            } else {
                for (HtmlElement htmlElement : directoryList) {
                    getHtml(baseUrl + htmlElement.getAttribute("href"), filesDtoList);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return filesDtoList;
    }

    private Long getBytes(HtmlPage htmlPage) {
        String[] bytesArray = ((HtmlDivision) htmlPage.getFirstByXPath("//div[@class='text-mono f6 flex-auto pr-3 flex-order-2 flex-md-order-1']")).asText().split(" ");
        Double bytes = Double.parseDouble(bytesArray[bytesArray.length - 2]);
        String unit = bytesArray[bytesArray.length - 1];

        switch (unit) {
            case "KB":
                bytes = bytes * 1000;
                break;
            case "Bytes":
            case "Byte":
                break;
        }

        return bytes.longValue();
    }

    private void addFileInList(String extension, Integer lines, String bytes, List<FilesDto> filesDtoList) {
        FilesDto file = new FilesDto();
        for (FilesDto item : filesDtoList) {
            if (item.getExtension().equals(extension)) {
                file = item;
                break;
            }
        }

        if (file.getExtension() == null) {
            filesDtoList.add(new FilesDto(extension, 1, lines.longValue(), Long.parseLong(bytes)));
        } else {
            file.setCount(file.getCount() + 1);
            file.setLines(file.getLines() + lines.longValue());
            file.setBytes(file.getBytes() + Long.parseLong(bytes));
        }
    }

    private String getExtension(String name) {
        String[] nameArray = name.split(Pattern.quote("."));
        String extension = nameArray[nameArray.length - 1];
        return extension;
    }
}
