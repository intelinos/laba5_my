package commands;

import exceptions.WrongArgumentException;
import exceptions.WrongArgumentInRequestInScriptException;
import exceptions.WrongNumberOfArgumentsException;
import managers.CollectionManager;
import managers.CommandManager;
import Organization.Organization;
import requesters.OrganizationReguester;
import utility.ScriptChecker;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Scanner;

/**
 * Класс команды update, которая обновляет значение элемента коллекции, id которого равен заданному.
 */
public class Update extends Command{
    Scanner scanner;
    CollectionManager collectionManager;
    CommandManager commandManager;
    public Update(CollectionManager collectionManager, CommandManager commandManager) {
        this.collectionManager = collectionManager;
        this.commandManager = commandManager;
        this.needScanner = true;
    }
    @Override
    public String getInfo() {
        return "Обновляет значение элемента коллекции, id которого равен заданному.";
    }

    @Override
    public String getName() {
        return "update";
    }

    /**
     * Проверяет количество аргументов команды и выполняет ее.
     * @param scanner Сканнер, который будет использоваться при выполнении команды. Выполнение команды прерывается, если введен id, не являющийся положительным числом типа int, а также если id не содержится в коллекции.
     * @param arguments Строка, содержащая имя команды и ее аргументы.
     * @throws WrongNumberOfArgumentsException Если количество аргументов команды не равно одному.
     */
    @Override
    public void execute(Scanner scanner, String[] arguments) throws WrongNumberOfArgumentsException{
        Map<Integer, Organization> collection = collectionManager.getCollection();
        if (collection.isEmpty()) {
            System.out.println("Коллекция пуста!");
            return;
        }
        if (arguments.length!=2) throw new WrongNumberOfArgumentsException();
        try {
            Integer id = Integer.parseInt(arguments[1]);
            if (ScriptChecker.isScriptInProcess) System.out.println("Введенный id = "+id);
            int key=0;
            for (Map.Entry<Integer, Organization> pair : collection.entrySet()) {
                if (id.equals(pair.getValue().getId()))
                    key = pair.getKey();
            }
            if (key == 0) throw new WrongArgumentException();
            Organization organization = new OrganizationReguester(scanner).getOrganization();
            organization.setId(id);
            LocalDateTime creationDate = collection.get(key).getCreationDate();
            organization.setCreationDate(creationDate);
            collectionManager.addToTheCollection(key, organization);
            System.out.println("Обновление элемента с id = "+id+" завершено.");
            if (ScriptChecker.isScriptInProcess) {
                System.out.println("Обновленный элемент: ");
                System.out.println(organization);
            }
        } catch (NumberFormatException e) {
            if (ScriptChecker.isScriptInProcess) {
                System.out.println("Произошла ошибка: id должно быть целым числом типа int.");
                ScriptChecker.clearScriptSet();
            } else System.out.println("Неверное значение id, необходимо ввести целое число типа int.");
        } catch (WrongArgumentException e) {
            if (ScriptChecker.isScriptInProcess) {
                System.out.println("Произошла ошибка: элемент с таким id должен существовать в текущей коллекции.");
                ScriptChecker.clearScriptSet();
            } else System.out.println("Недопустимое значение id, элемента с таким id не существует.");
        } catch (WrongArgumentInRequestInScriptException e) {
            System.out.println("Произошла ошибка: "+e.getMessage());
            ScriptChecker.clearScriptSet();
        }
    }
}
