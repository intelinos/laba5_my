import Organization.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import exceptions.WrongArgumentException;
import exceptions.WrongDeserializationError;
import managers.CollectionManager;
import managers.CommandManager;
import managers.FileManager;

import java.io.*;
import java.time.DateTimeException;
import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeMap;
import java.util.Scanner;
import java.time.LocalDateTime;


public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Требуется передать имя файла как аргумент командной строки.");
            System.exit(1);
        }
        try {
            FileManager fileManager = new FileManager(args[0]);
            CollectionManager collectionManager = new CollectionManager(fileManager);
            collectionManager.loadCollectionFromFile();
            Scanner scanner = new Scanner(System.in);
            CommandManager commandManager = new CommandManager(collectionManager);
            while (true) {
                try {
                    commandManager.invokeCommand(scanner);
                } catch (NoSuchElementException e) {
                    System.out.println("Неверный ввод.");
                    scanner = new Scanner(System.in);
                }
            }
        } catch (WrongDeserializationError | JsonSyntaxException | DateTimeParseException e) {
            System.out.println("Неверные данные в файле, ошибка чтения:");
            System.out.println(e.getMessage());
        } catch (FileNotFoundException e) {
            if (!FileManager.file.exists()) {
                System.out.println("Файл с данным именем не найден!");
            } else if (!FileManager.file.canRead()) {
                System.out.println("Отсутствуют права на чтение этого файла!");
            }
        } catch (IOException e) {
            System.out.println("Возникла ошибка: IOException");
        }
    }
}