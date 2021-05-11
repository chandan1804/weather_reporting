package com.testvagrant.page;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import com.testvagrant.utils.SeleniumPage;
import okhttp3.*;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.io.*;
import java.util.List;
import java.util.Scanner;

/**
 * @author Chandan
 */

public class WeatherPage extends SeleniumPage {
    @FindBy(xpath = "//input[@type='checkbox']")
    List<WebElement> cityCheckBox;
    @FindBy(css = "#searchBox")
    WebElement citySearchField;
    @FindBy(css = ".leaflet-popup-content-wrapper")
    WebElement weatherDetailPopup;

    String cityName = "";
    String weatherDetails;
    int range = 0;

    /**
     * Method to uncheck all the default selected check boxes
     */
    public void uncheckDefaultCityChecked() {
        List<WebElement> checkBox = cityCheckBox;
        for (WebElement e : checkBox) {
            String cityName = e.getAttribute("id");
            try {
                WebElement checkedCity = e.findElement(By.xpath("//input[@id='" + cityName + "']"));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", checkedCity);
                if (checkedCity.isSelected()) {
                    click(checkedCity);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Method to search city name and select the check box
     *
     * @param cityName user input city name provided in the TestData.csv file
     */
    public void selectCityFromDropdown(String cityName) {
        uncheckDefaultCityChecked();
        sendKeys(citySearchField, cityName);
        click(driver.findElement(By.cssSelector("#" + cityName)));
    }

    /**
     * Method to get Weather details from the map popup and store it in file
     *
     * @param cityName user input city name provided in the TestData.csv file
     */
    public void getWeatherDetails(String cityName) {
        click(driver.findElement(By.xpath("//div[@title='" + cityName + "']")));
        weatherDetails = weatherDetailPopup.getText();
        try {
            File file = new File("src/main/resources/weatherdetails.txt");
            FileUtils.writeStringToFile(file, weatherDetails, "UTF-8");
        } catch (IOException es) {
            es.printStackTrace();
        }
    }

    /**
     * Method to get temperature of the particular city from the popup displayed on the map
     *
     * @return returns temperature on celsius
     */
    public int getUITemperature() {
        int temperature = 0;
        try {
            Scanner scanner = new Scanner(new File("src/main/resources/weatherdetails.txt"));
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                if (line.contains("Temp in Degrees")) {
                    String ln[] = line.split(":");
                    temperature = Integer.parseInt(ln[1].trim());
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return temperature;
    }

    /**
     * Method to get temperature of the city through API
     *
     * @return returns temperature in celsius
     * @throws IOException
     */
    public int getAPITemperature() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("https://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&appid=7fe67bf08c80ded756e598d6f8fedaea")
                .method("POST", body)
                .build();
        Response response = client.newCall(request).execute();
        assert response.body() != null;
        String resp = response.body().string();
        JSONObject json = new JSONObject(resp);
        JSONObject temp = new JSONObject(json.get("main").toString());
        String temperatureAPI = temp.get("temp").toString();
        return Integer.parseInt(String.valueOf(Math.round(Float.parseFloat(String.valueOf((Float.parseFloat(temperatureAPI) - 273.15))))));
    }

    /**
     * Method to compare temperature from the UI and API
     */
    public void tempCompare() {
        String result = "PASS";
        File testData = new File("src/main/resources/TestData.csv");
        File resultData = new File("src/main/resources/TestResult.csv");
        try {
            CSVReader reader = new CSVReader(new FileReader(testData));
            CSVWriter writer = new CSVWriter(new FileWriter(resultData));
            String[] cell;
            boolean headersConsumed = false;
            String[] header = {"City", "UI Temperature", "API Temperature", "Temperature Range", "Result"};
            writer.writeNext(header);
            while ((cell = reader.readNext()) != null) {
                if (!headersConsumed) { // if headers not consumed
                    headersConsumed = true;
                    continue; // skip line with headers
                }
                cityName = cell[0];
                range = Integer.parseInt(cell[1]);
                selectCityFromDropdown(cityName);
                getWeatherDetails(cityName);
                int tempUI = getUITemperature();
                int tempAPI = 0;
                try {
                    tempAPI = getAPITemperature();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                int tempDiff = Math.abs(tempUI - tempAPI);
                try {
                    if (tempDiff >= range) {
                        result = "FAIL";
                        throw new UserDefinedException("Temperature not in range for the city " + cityName);
                    }
                } catch (UserDefinedException ex) {
                    Assert.fail(String.valueOf(ex));
                }
                String[] data = {cityName, String.valueOf(tempUI), String.valueOf(tempAPI), String.valueOf(range), result};
                writer.writeNext(data);
            }
            writer.close();
        } catch (CsvValidationException | IOException e) {
            e.printStackTrace();
        }
    }

    static class UserDefinedException extends Exception {
        String errorMessage;

        UserDefinedException(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        @Override
        public String toString() {
            return errorMessage;
        }
    }
}
