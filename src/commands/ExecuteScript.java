package commands;

import exceptions.ScriptRecursionException;
import exceptions.WrongNumberOfArgumentsException;
import managers.CollectionManager;
import managers.CommandManager;
import utility.ScriptChecker;

import java.io.*;
import java.util.Scanner;

/**
 * Класс команды execute_script, которая считывает и исполняет скрипт из указанного файла.
 */
public class ExecuteScript extends Command{

    CommandManager commandManager;
    CollectionManager collectionManager;

    public ExecuteScript(CommandManager commandManager, CollectionManager collectionManager) {
        this.commandManager=commandManager;
        this.collectionManager=collectionManager;
        this.needScanner=false;
    }

    @Override
    public String getInfo() {
        return "Считывает и исполняет скрипт из указанного файла.";
    }

    @Override
    public String getName() {
        return "execute_script";
    }

    /**
     * Проверяет количество аргументов команды execute_script и выполняет ее. Выполнение прерывается если не был найден файл скрипта/ нет прав на чтение этого файла, а также если была обнаружена рекурсия.
     * @param arguments Строка, содержащая имя команды и ее аргументы.
     * @throws WrongNumberOfArgumentsException Если не было введено аргументов команды, либо их количество больше одного.
     */
    @Override
    public void execute(String[] arguments) throws WrongNumberOfArgumentsException {
        if (arguments.length!=2) throw new WrongNumberOfArgumentsException();
        String fileName = arguments[1];
        try {
            File file = new File(fileName);
            if (!file.exists()) throw new FileNotFoundException();
            System.out.println("Начинаем выполнять скрипт "+fileName);
            if (file.length()==0) {
                System.out.println("Файл "+fileName+" пуст.");
                return;
            }
            if(!ScriptChecker.addInScriptSet(file)) throw new ScriptRecursionException();
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                ScriptChecker.isScriptInProcess=true;
                commandManager.invokeCommand(scanner);
                if(ScriptChecker.isScriptSetEmpty()) {
                    ScriptChecker.isScriptInProcess=false;
                    return;
                }
            }
            System.out.println("Скрипт "+fileName+" успешно выполнен.");
            ScriptChecker.isScriptInProcess=false;
            ScriptChecker.clearScriptSet();
        } catch (FileNotFoundException e) {
            System.out.println("Файл с именем" +fileName+" не существует.");
            ScriptChecker.clearScriptSet();
        } catch (ScriptRecursionException e) {
            System.out.println("Обнаружена рекурсия! Скрипт - "+fileName);
            ScriptChecker.clearScriptSet();
        }
    }
}
