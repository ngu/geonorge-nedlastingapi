package no.geonorge.nedlasting.data.upgrade;

import java.util.Date;
import java.util.List;

import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.exp.ExpressionFactory;
import org.apache.cayenne.query.SelectQuery;

import no.geonorge.nedlasting.config.Config;
import no.geonorge.nedlasting.data.DatasetFile;

public class DatasetFileUpgrade extends EntityUpgrade {

    @Override
    public void upgrade() {
        ObjectContext ctxt = Config.getObjectContext();

        Expression qual = ExpressionFactory.matchExp(DatasetFile.FILE_DATE_PROPERTY, null);
        SelectQuery query = new SelectQuery(DatasetFile.class, qual);

        @SuppressWarnings("unchecked")
        List<DatasetFile> dsfs = ctxt.performQuery(query);

        for (DatasetFile dsf : dsfs) {
            if (dsf.getFileDate() == null) {
                dsf.setFileDate(new Date());
            }
        }

        ctxt.commitChanges();
    }

}
