package id.poros.filkom.col_lab.Interface;

import id.poros.filkom.col_lab.model.AgendaContent;
import id.poros.filkom.col_lab.model.EventContent;
import id.poros.filkom.col_lab.model.OrganizationContent;

public interface OnFragmentInteractionListener {
    void onListFragmentInteraction(OrganizationContent.OrganizationItem item);
    void onListFragmentInteraction(AgendaContent.AgendaItem item);
    void onListFragmentInteraction(EventContent.EventItem item);
}
