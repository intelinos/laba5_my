package commands;

import exceptions.WrongNumberOfArgumentsException;
import managers.CollectionManager;
import managers.CommandManager;

/**
 * Класс команды info, которая выводит в стандартный поток вывода информацию о коллекции.
 */
public class Info extends Command{
    CollectionManager collectionManager;
    public Info(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
        this.needScanner = false;
    }
    @Override
    public String getInfo() {
        return "Выводит в стандартный поток вывода информацию о коллекции.";
    }

    @Override
    public String getName() {
        return "info";
    }
    /**
     * Проверяет количество аргументов комманды и выполняет ее.
     * @param arguments Строка, содержащая имя команды и ее аргументы.
     * @throws WrongNumberOfArgumentsException Если были введены аргументы команды.
     */
    @Override
    public void execute(String[] arguments) throws WrongNumberOfArgumentsException {
        if (arguments.length!=1) throw new WrongNumberOfArgumentsException();
        System.out.println("Информация о коллекции:\n" +
                "Тип коллекции: "+collectionManager.getCollection().getClass().getName() +"\n"+
                "Размер коллекции: "+collectionManager.getCollection().size() +"\n"+
                "Время инициализации: "+collectionManager.getInitiationDate() +"\n"+
                "Время последнего сохранения: "+collectionManager.getLastTimeOfSaving());
    }
}
