package za.co.einsight.templates.model;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.joanzapata.iconify.Icon;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import za.co.einsight.templates.R;
import za.co.einsight.templates.simplelistview.ListModel;

@SuppressWarnings("unused")
public class Person implements ListModel {

    private static final Icon ICON = FontAwesomeIcons.fa_user;
    private String firstNames;
    private String surname;
    private String emailAddress;

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getFirstNames() {
        return firstNames;
    }

    public void setFirstNames(String firstNames) {
        this.firstNames = firstNames;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public String getMainText() {
        return firstNames + " " + surname;
    }

    @Override
    public String getSubText() {
        return emailAddress;
    }

    @Override
    public Drawable getImage(Context context) {
        return new IconDrawable(context, ICON)
                .colorRes(R.color.colorPrimary)
                .actionBarSize();
    }
}
