package com.pipiolo.springbatch.dto;

import lombok.Data;

@Data
public class PlayerSalaryDto {

    private String ID;
    private String lastName;
    private String firstName;
    private String position;
    private int birthYear;
    private int debutYear;
    private int salary;

    public static PlayerSalaryDto of(PlayerDto playerDto, int salary) {
        PlayerSalaryDto playerSalaryDto = new PlayerSalaryDto();
        playerSalaryDto.setID(playerSalaryDto.getID());
        playerSalaryDto.setLastName(playerSalaryDto.getLastName());
        playerSalaryDto.setFirstName(playerSalaryDto.getFirstName());
        playerSalaryDto.setPosition(playerSalaryDto.getPosition());
        playerSalaryDto.setBirthYear(playerDto.getBirthYear());
        playerSalaryDto.setDebutYear(playerSalaryDto.getDebutYear());
        playerSalaryDto.setSalary(salary);

        return playerSalaryDto;
    }
}
