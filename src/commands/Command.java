package commands;

import exceptions.WrongNumberOfArgumentsException;

import java.util.Scanner;

/**
 * Абстрактная команда.
 */
public abstract class Command implements Executable,Infoable {
    public boolean needScanner;

    /**
     * @return Описание команды.
     */
    @Override
    public abstract String getInfo();

    /**
     * @return Имя команды.
     */
    @Override
    public abstract String getName();

    /**
     * Запускает команду, которая не требует сканер.
     * @param arguments Строка, содержащая имя команды и ее аргументы.
     * @throws WrongNumberOfArgumentsException Если было введено неверное количество аргументов команды.
     */
    @Override
    public void execute(String[] arguments) throws WrongNumberOfArgumentsException  {};
    /**
     * Запускает команду, которая требует сканер.
     * @param arguments Строка, содержащая имя команды и ее аргументы.
     * @throws WrongNumberOfArgumentsException Если было введено неверное количество аргументов команды.
     */
    @Override
    public void execute(Scanner scanner, String[] arguments) throws WrongNumberOfArgumentsException {};





}
