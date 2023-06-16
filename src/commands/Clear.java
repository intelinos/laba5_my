package commands;

import exceptions.WrongNumberOfArgumentsException;
import managers.CollectionManager;

/**
 * Класс команды clear, которая очищает коллекцию.
 */
public class Clear extends Command{
    CollectionManager collectionManager;
    public Clear(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
        needScanner = false;
    }
    @Override
    public String getInfo() {
        return "Очищает коллекцию.";
    }

    @Override
    public String getName() {
        return "clear";
    }

    /**
     * Проверяет количество аргументов команды clear и выполняет ее.
     * @param arguments Строка, содержащая имя команды и ее аргументы.
     * @throws WrongNumberOfArgumentsException Если у команды clear был введен один или несколько аргументов.
     */
    @Override
    public void execute(String[] arguments) throws WrongNumberOfArgumentsException {
        if (arguments.length>1) throw new WrongNumberOfArgumentsException();
        if (collectionManager.getCollection().isEmpty()) {
            System.out.println("Коллекция и так пуста!");
            return;
        }
        collectionManager.getCollection().clear();
        System.out.println("Коллекция была очищена.");
    }
}
