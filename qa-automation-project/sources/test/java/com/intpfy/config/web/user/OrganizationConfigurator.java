package com.intpfy.config.web.user;

import com.intpfy.config.web.BaseConfigurator;
import com.intpfy.gui.dialogs.emi.AllowlistDW;
import com.intpfy.gui.pages.emi.DashboardPage;
import com.intpfy.gui.pages.emi.UsersPage;
import com.intpfy.gui.pages.emi.user_creation.EditUserPage;
import com.intpfy.model.user.OrganizationModel;
import com.intpfy.authorization.Authorizer;
import com.intpfy.util.UserUtil;
import com.intpfy.verifiers.emi.user_creation.EditUserPageVerifier;
import com.intpfy.verifiers.emi.users_page.UsersPageVerifier;

import java.util.Set;

public class OrganizationConfigurator extends BaseConfigurator {

    private static final OrganizationConfigurator instance = new OrganizationConfigurator(Authorizer.getInstance());

    private OrganizationConfigurator(Authorizer authorizer) {
        super(authorizer);
    }

    public static OrganizationConfigurator getInstance() {
        return instance;
    }

    public UsersPage edit(OrganizationModel model) {

        EditUserPage page = openEditUserPage(model);
        page.assertIsOpened();

        setDomainName(page, model);

        applyActiveDirectorySettings(page, model);
        applyKeycloakSettings(page, model);

        return page.save();
    }

    private EditUserPage openEditUserPage(OrganizationModel model) {

        DashboardPage dashboardPage = authorizer.logInToEMI(UserUtil.getAdminUser());
        dashboardPage.assertIsOpened();

        UsersPage usersPage = dashboardPage.goToUsersPage();
        usersPage.assertIsOpened();

        String organizationLogin = model.getLogin();

        usersPage.searchIfNotDisplayed(organizationLogin);

        UsersPageVerifier usersPageVerifier = new UsersPageVerifier(usersPage);
        usersPageVerifier.assertUserDisplayed(organizationLogin);

        return usersPage.edit(model.getLogin());
    }

    private void setDomainName(EditUserPage page, OrganizationModel model) {

        String domainName = model.getDomain();

        if (domainName != null) {

            page.setDomainName(domainName);

            EditUserPageVerifier editUserPageVerifier = new EditUserPageVerifier(page);
            editUserPageVerifier.assertDomainNameEquals(domainName);
        }
    }

    private void applyActiveDirectorySettings(EditUserPage page, OrganizationModel model) {

        EditUserPageVerifier verifier = new EditUserPageVerifier(page);

        String samlIdProviderCode = model.getSamlIdProviderCode();

        if (samlIdProviderCode != null) {

            page.enableActiveDirectoryAuthentication();
            verifier.assertActiveDirectoryAuthenticationEnabled();

            page.setActiveDirectoryIdentifier(samlIdProviderCode);
            verifier.assertActiveDirectoryIdentifierEquals(samlIdProviderCode);

            setSamlRequiredGroup(page, model);
            createAllowlist(page, model);

        } else {
            page.disableActiveDirectoryAuthentication();
            verifier.assertActiveDirectoryAuthenticationDisabled();
        }
    }

    private void applyKeycloakSettings(EditUserPage page, OrganizationModel model) {

        EditUserPageVerifier verifier = new EditUserPageVerifier(page);

        if (model.isKeycloakEnabled()) {

            page.enableKeycloak();
            verifier.assertKeycloakEnabled();

            setSamlRequiredGroup(page, model);
            createAllowlist(page, model);

        } else {
            page.disableKeycloak();
            verifier.assertKeycloakDisabled();
        }
    }


    private void setSamlRequiredGroup(EditUserPage page, OrganizationModel model) {

        EditUserPageVerifier verifier = new EditUserPageVerifier(page);

        String samlRequiredGroup = model.getSamlRequiredGroup();

        if (samlRequiredGroup == null) {
            page.removeSecurityGroup();
            verifier.assertSecurityGroupEmpty();
        } else {
            page.setSecurityGroup(samlRequiredGroup);
            verifier.assertSecurityGroupEquals(samlRequiredGroup);
        }
    }

    private void createAllowlist(EditUserPage page, OrganizationModel model) {

        EditUserPageVerifier verifier = new EditUserPageVerifier(page);

        page.removeAllowlist();
        verifier.assertAllowlistNotCreated();

        Set<String> allowlistEmails = model.getAllowlistEmails();

        if (!allowlistEmails.isEmpty()) {

            AllowlistDW allowlistDW = page.createAllowlist();
            allowlistDW.assertIsOpened();

            allowlistEmails.forEach(allowlistDW::addEmail);

            allowlistDW.confirm();
            allowlistDW.assertNotVisible();

            verifier.assertAllowlistCreated();
        }
    }
}
