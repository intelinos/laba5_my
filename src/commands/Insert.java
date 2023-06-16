package commands;

import exceptions.WrongArgumentException;
import exceptions.WrongArgumentInRequestInScriptException;
import exceptions.WrongNumberOfArgumentsException;
import managers.*;
import Organization.*;
import requesters.OrganizationReguester;
import utility.ScriptChecker;
import validators.KeyValidator;
import validators.Validator;

import java.util.Scanner;

/**
 * Класс команды insert, которая добавляет в коллекцию элемент с заданным ключом.
 */
public class Insert extends Command {
    CollectionManager collectionManager;

    private Validator<Integer> keyValidator = new KeyValidator();
    public Insert(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
        this.needScanner=true;
    }

    @Override
    public String getInfo() {
        return "Добавляет в коллекцию элемент с заданным ключом.";
    }

    @Override
    public String getName() {
        return "insert";
    }

    /**
     * Проверяет количество аргументов команды и выполняет ее. Выполнение команды прерывается, если введен ключ, не являющийся положительным числом типа int, а также если ключ уже содержится в коллекции.
     * @param scanner Сканнер, который будет использоваться при выполнении команды.
     * @param arguments Строка, содержащая имя команды и ее аргументы.
     * @throws WrongNumberOfArgumentsException Если количество аргументов команды не равно одному.
     */
    @Override
    public void execute(Scanner scanner, String[] arguments) throws WrongNumberOfArgumentsException {
        if (arguments.length != 2) throw new WrongNumberOfArgumentsException();
        try {
            int key = Integer.parseInt(arguments[1].strip());
            if (ScriptChecker.isScriptInProcess) System.out.println("Введенный ключ: " + key);
            if (!keyValidator.validate(key) || collectionManager.getCollection().containsKey(key)) throw new WrongArgumentException();
            Organization organization = new OrganizationReguester(scanner).getOrganization();
            collectionManager.addToTheCollection(key, organization);
            System.out.println("Вставка элемента с ключом " + key + " завершена.");
            if (ScriptChecker.isScriptInProcess) {
                System.out.println("Новый элемент: ");
                System.out.println(organization);
            }
        } catch (NumberFormatException  e){
            if (ScriptChecker.isScriptInProcess) {
                System.out.println("Произошла ошибка: ключ должен быть целым числом типа int.");
                ScriptChecker.clearScriptSet();
            } else System.out.println("Неверное значение ключа, необходимо ввести целое число типа int.");
        } catch (WrongArgumentException e) {
            if (ScriptChecker.isScriptInProcess) {
                System.out.println("Произошла ошибка: ключ должен быть положительным и уникальным!");
                ScriptChecker.clearScriptSet();
            } else System.out.println("Неверное значение ключа, ключ должен быть положительным и уникальным!");
        } catch (WrongArgumentInRequestInScriptException e) {
            System.out.println("Произошла ошибка: "+e.getMessage());
            ScriptChecker.clearScriptSet();
        }
    }
}
