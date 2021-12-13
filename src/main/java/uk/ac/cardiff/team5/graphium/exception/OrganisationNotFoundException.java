package uk.ac.cardiff.team5.graphium.exception;

public class OrganisationNotFoundException extends Exception {
    public OrganisationNotFoundException(Long id) {
        super("Could not find organisation with id: " + id);
    }
}
