package com.aa.act.interview.org;

import java.util.Optional;

public abstract class Organization {

    private int numberOfEmployees = 0;

    private Position root;
    
    public Organization() {
        root = createOrganization();
    }
    
    protected abstract Position createOrganization();
    
    /**
     * hire the given person as an employee in the position that has that title
     * 
     * @param person
     * @param title
     * @return the newly filled position or empty if no position has that title
     */
    public Optional<Position> hire(Name person, String title) {
        return fillPosition(root, person, title);
    }

    @Override
    public String toString() {
        return printOrganization(root, "");
    }

    private Optional<Position> fillPosition(Position organizationGroup, Name person, String title) {
        for(Position position : organizationGroup.getDirectReports()) {
            if (position.getTitle().equalsIgnoreCase(title)) {
                Employee newEmployee = new Employee(numberOfEmployees + 1, person);
                position.setEmployee(newEmployee);
                numberOfEmployees++;
                return Optional.of(position);
            } else {
                fillPosition(position, person, title);
            }
        }

        return Optional.empty();
    }
    
    private String printOrganization(Position pos, String prefix) {
        StringBuffer sb = new StringBuffer(prefix + "+-" + pos.toString() + "\n");
        for(Position p : pos.getDirectReports()) {
            sb.append(printOrganization(p, prefix + "  "));
        }
        return sb.toString();
    }
}
