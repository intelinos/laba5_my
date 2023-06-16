package commands;

import exceptions.WrongNumberOfArgumentsException;
import managers.CommandManager;

import java.util.Map;

/**
 * Класс команды help, которая выводит справку о доступных командах.
 */
public class Help extends Command {
    CommandManager commandManager;

    public Help(CommandManager commandManager) {
        this.commandManager =  commandManager;
    }

    @Override
    public String getInfo() {
        return "Выводит справку о доступных командах. ";
    }

    @Override
    public String getName() {
        return "help";
    }

    /**
     * Проверяет количество аргументов команды и выполняет ее.
     * @param arguments Строка, содержащая имя команды и ее аргументы.
     * @throws WrongNumberOfArgumentsException Если были введены аргументы команды.
     */
    @Override
    public void execute(String[] arguments) throws WrongNumberOfArgumentsException {
        if (arguments.length>1) throw new WrongNumberOfArgumentsException();
        for (Map.Entry<String, Command> pair : commandManager.getCommands().entrySet())
        {
            String name = pair.getKey();
            Command command = pair.getValue();
            System.out.println(name + ": " + command.getInfo());
        }
    }
}
