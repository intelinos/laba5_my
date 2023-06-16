package commands;

import exceptions.WrongArgumentException;
import exceptions.WrongNumberOfArgumentsException;
import managers.CollectionManager;
import utility.ScriptChecker;
import validators.KeyValidator;
import validators.Validator;

/**
 * Класс команды remove, которая удаляет из коллекции элемент с заданным ключом.
 */
public class Remove extends Command{
    private CollectionManager collectionManager;

    private Validator<Integer> keyValidator = new KeyValidator();
    public Remove(CollectionManager collectionManager) {;
        this.collectionManager = collectionManager;
        this.needScanner = false;
    }
    @Override
    public String getInfo() {
        return "Удаляет из коллекции элемент с заданным ключом.";
    }

    @Override
    public String getName() {
        return "remove";
    }

    /**
     * Проверяет количество аргументов команды и выполняет ее. Выполнение команды прерывается, если введен ключ, не являющийся положительным числом типа int, а также если ключ не содержится в коллекции.
     * @param arguments Строка, содержащая имя команды и ее аргументы.
     * @throws WrongNumberOfArgumentsException Если количество аргументов команды не равно одному.
     */
    @Override
    public void execute(String[] arguments) throws WrongNumberOfArgumentsException {
        if (collectionManager.getCollection().isEmpty()) {
            System.out.println("Коллекция пуста!");
            return;
        }
        if (arguments.length != 2) throw new WrongNumberOfArgumentsException();
        try {
            int key = Integer.parseInt(arguments[1]);
            if (ScriptChecker.isScriptInProcess) System.out.println("Введенный ключ: " + key);
            if (!keyValidator.validate(key) || !collectionManager.getCollection().containsKey(key))
                throw new WrongArgumentException();
            collectionManager.deleteFromTheCollection(key);
            System.out.println("Элемент в ключом " + key + " был удален.");
        } catch (NumberFormatException e) {
            if (ScriptChecker.isScriptInProcess) {
                System.out.println("Произошла ошибка: ключ должен быть целым числом типа int.");
                ScriptChecker.clearScriptSet();
            } else System.out.println("Неверное значение ключа, необходимо ввести целое число типа int.");
        } catch (WrongArgumentException e) {
            if (ScriptChecker.isScriptInProcess) {
                System.out.println("Произошла ошибка: ключ должен быть положительным и существовать в текущей коллекции.!");
                ScriptChecker.clearScriptSet();
            } else
                System.out.println("Неверное значение ключа: ключ должен быть положительным и существовать в текущей коллекции.");
        }
    }
}
