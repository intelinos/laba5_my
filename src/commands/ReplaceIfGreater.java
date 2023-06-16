package commands;

import Organization.Organization;
import exceptions.WrongArgumentException;
import exceptions.WrongArgumentInRequestInScriptException;
import exceptions.WrongNumberOfArgumentsException;
import managers.CollectionManager;
import requesters.OrganizationReguester;
import utility.ScriptChecker;
import validators.KeyValidator;
import validators.Validator;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

/**
 * Класс команды replace_if_greater, которая заменяет элемент коллекции, если новый элемент больше старого.
 */
public class ReplaceIfGreater extends Command{
    CollectionManager collectionManager;
    private Validator<Integer> keyValidator = new KeyValidator();
    public ReplaceIfGreater(CollectionManager collectionManager) {
        this.needScanner=true;
        this.collectionManager = collectionManager;
    }
    @Override
    public String getInfo() { return " Заменяет значение по ключу, если новое значение больше старого."; }

    @Override
    public String getName() { return "replace_if_greater"; }

    /**
     * Проверяет количество аргументов команды и выполняет ее. Выполнение команды прерывается, если введен ключ, не являющийся положительным числом типа int, а также если ключ не содержится в коллекции.
     * @param arguments Строка, содержащая имя команды и ее аргументы.
     * @throws WrongNumberOfArgumentsException Если количество аргументов команды не равно одному.
     */
    @Override
    public void execute(Scanner scanner, String[] arguments) throws WrongNumberOfArgumentsException {
        Map<Integer, Organization> collection = collectionManager.getCollection();
        if (collection.isEmpty()) {
            System.out.println("Коллекция пуста!");
            return;
        }
        if (arguments.length!=2) throw new WrongNumberOfArgumentsException();
        try {
            int key = Integer.parseInt(arguments[1].strip());
            if (ScriptChecker.isScriptInProcess) System.out.println("Введенный ключ: " + key);
            if (!keyValidator.validate(key) || !collection.containsKey(key)) throw new WrongArgumentException();
            Organization organization = new OrganizationReguester(scanner).getOrganization();
            if (organization.compareTo(collection.get(key)) > 0) {
                collectionManager.addToTheCollection(key, organization);
                System.out.println("Новое значение больше старого, элемент заменен.");
                if (ScriptChecker.isScriptInProcess) {
                    System.out.println("Новый элемент: ");
                    System.out.println(organization);
                }
            } else {
                System.out.println("Новое значение не больше старого, исходный элемент не заменен.");
                System.out.println("Элемент, который был введен: ");
                System.out.println(organization);
            }
        }  catch (WrongArgumentException e) {
            if (ScriptChecker.isScriptInProcess) {
                System.out.println("Произошла ошибка: ключ должен существовать в текущей коллекции и быть положительным.");
                ScriptChecker.clearScriptSet();
            } else System.out.println("Неверное значение ключа, ключ должен существовать в текущей коллекции и быть положительным!");
        } catch (NumberFormatException e) {
            if (ScriptChecker.isScriptInProcess) {
                System.out.println("Произошла ошибка: ключ должен быть целым числом типа int.");
                ScriptChecker.clearScriptSet();
            } else System.out.println("Неверное значение ключа, необходимо ввести целое число типа int.");
        }  catch (WrongArgumentInRequestInScriptException e) {
            System.out.println("Произошла ошибка: "+e.getMessage());
            ScriptChecker.clearScriptSet();
        }
    }
}
