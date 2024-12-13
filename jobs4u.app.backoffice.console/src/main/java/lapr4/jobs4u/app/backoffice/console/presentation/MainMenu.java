/*
 * Copyright (c) 2013-2024 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package lapr4.jobs4u.app.backoffice.console.presentation;

import lapr4.jobs4u.Application;
import lapr4.jobs4u.app.backoffice.console.presentation.authz.*;
import lapr4.jobs4u.app.common.console.presentation.authz.MyUserMenu;
import lapr4.jobs4u.candidatemanagement.domain.Pair;
import lapr4.jobs4u.usermanagement.domain.Roles;
import eapli.framework.actions.Actions;
import eapli.framework.actions.menu.Menu;
import eapli.framework.actions.menu.MenuItem;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.ExitWithMessageAction;
import eapli.framework.presentation.console.ShowMessageAction;
import eapli.framework.presentation.console.menu.HorizontalMenuRenderer;
import eapli.framework.presentation.console.menu.MenuItemRenderer;
import eapli.framework.presentation.console.menu.MenuRenderer;
import eapli.framework.presentation.console.menu.VerticalMenuRenderer;

import eapli.framework.actions.Action;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO split this class in more specialized classes for each menu
 *
 * @author Paulo Gandra Sousa
 */
public class MainMenu extends AbstractUI {

	private static final String RETURN_LABEL = "Return ";

	private static final int EXIT_OPTION = 0;

	// USERS
	private static final int ADD_USER_OPTION = 1;
	private static final int LIST_USERS_OPTION = 2;
	private static final int DEACTIVATE_USER_OPTION = 3;
	private static final int ACCEPT_REFUSE_SIGNUP_REQUEST_OPTION = 4;

	private static final int USER_OPTION_5 = 5;

	private static final int USER_OPTION_6 = 6;

	private static final int USER_OPTION_7 = 7;

	private static final int USER_OPTION_8 = 8;

	private static final int USER_OPTION_9 = 9;

	// SETTINGS
	private static final int SET_KITCHEN_ALERT_LIMIT_OPTION = 1;

	// MAIN MENU
	private static final int MY_USER_OPTION = 1;
	private static final int USERS_OPTION = 2;
	private static final int SETTINGS_OPTION = 4;

	private static final String SEPARATOR_LABEL = "--------------";


	private final AuthorizationService authz = AuthzRegistry.authorizationService();

	@Override
	public boolean show() {
		drawFormTitle();
		return doShow();
	}

	/**
	 * @return true if the user selected the exit option
	 */
	@Override
	public boolean doShow() {
		final var menu = buildMainMenu();
		final MenuRenderer renderer;
		if (Application.settings().isMenuLayoutHorizontal()) {
			renderer = new HorizontalMenuRenderer(menu, MenuItemRenderer.DEFAULT);
		} else {
			renderer = new VerticalMenuRenderer(menu, MenuItemRenderer.DEFAULT);
		}
		return renderer.render();
	}

	@Override
	public String headline() {

		return authz.session().map(s -> "Backoffice [ @" + s.authenticatedUser().identity() + " ]")
				.orElse("Backoffice [ ==Anonymous== ]");
	}

	private Menu buildMainMenu() {
		final var mainMenu = new Menu();

		final Menu myUserMenu = new MyUserMenu();
		mainMenu.addSubMenu(MY_USER_OPTION, myUserMenu);

		if (!Application.settings().isMenuLayoutHorizontal()) {
			mainMenu.addItem(MenuItem.separator(SEPARATOR_LABEL));
		}

		if (authz.isAuthenticatedUserAuthorizedTo(Roles.ADMIN)) {
			List<Pair<String, Action>> userMenuBuilder = buildUsersMenu();
			userMenuBuilder.addAll(buildCustomerManagerMenu());
			userMenuBuilder.addAll(buildOperatorMenu());
			userMenuBuilder.addAll(buildLanguageEngineerMenu());
			final var usersMenu = buildMenu(userMenuBuilder, "Users >");
			mainMenu.addSubMenu(USERS_OPTION, usersMenu);
			final var settingsMenu = buildAdminSettingsMenu();
			mainMenu.addSubMenu(SETTINGS_OPTION, settingsMenu);
		}

		if (authz.isAuthenticatedUserAuthorizedTo(Roles.CUSTOMER_MANAGER)) {
			List<Pair<String, Action>> userMenuBuilder = buildCustomerManagerMenu();
			userMenuBuilder.addAll(buildOperatorMenu());
			final var usersMenu = buildMenu(userMenuBuilder, "Users >");
			mainMenu.addSubMenu(USERS_OPTION, usersMenu);
		}

		if (authz.isAuthenticatedUserAuthorizedTo(Roles.OPERATOR)) {
			List<Pair<String, Action>> userMenuBuilder = buildOperatorMenu();
			final var usersMenu = buildMenu(userMenuBuilder, "Users >");
			mainMenu.addSubMenu(USERS_OPTION, usersMenu);
		}

		if (authz.isAuthenticatedUserAuthorizedTo(Roles.LANGUAGE_ENGINEER)) {
			List<Pair<String, Action>> userMenuBuilder = buildLanguageEngineerMenu();
			final var usersMenu = buildMenu(userMenuBuilder, "Users >");
			mainMenu.addSubMenu(USERS_OPTION, usersMenu);
		}

		if (!Application.settings().isMenuLayoutHorizontal()) {
			mainMenu.addItem(MenuItem.separator(SEPARATOR_LABEL));
		}

		mainMenu.addItem(EXIT_OPTION, "Exit", new ExitWithMessageAction("Bye, Bye"));

		return mainMenu;
	}

	private Menu buildMenu(List<Pair<String, Action>> menuBuilder, String title) {
		final var menu = new Menu(title);
		int index = 1;
		for (Pair<String, Action> item: menuBuilder) {
			menu.addItem(index, item.getLeft(), item.getRight());
			index++;
		}
		menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);
		return menu;
	}

	private Menu buildAdminSettingsMenu() {
		final var menu = new Menu("Settings >");

		menu.addItem(SET_KITCHEN_ALERT_LIMIT_OPTION, "Set kitchen alert limit",
				new ShowMessageAction("Not implemented yet"));
		menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);

		return menu;
	}

	private List<Pair<String, Action>> buildUsersMenu() {
		List<Pair<String, Action>> menuL = new ArrayList<>();

		menuL.add(new Pair<>("Add User", new AddUserUI()::show));
		menuL.add(new Pair<>("List all Users", new ListUsersAction()));
		menuL.add(new Pair<>("Disable or Enable User", new DeactivateUserAction()));
		menuL.add(new Pair<>("Accept/Refuse Signup Request", new DeactivateUserAction()));

		return menuL;
	}

	private List<Pair<String, Action>> buildOperatorMenu() {
		List<Pair<String, Action>> menuL = new ArrayList<>();

		menuL.add(new Pair<>("Register Candidate", new RegisterCandidateUI()::show));
		menuL.add(new Pair<>("List all Candidates", new ListAllCandidatesUI()::show));
        menuL.add(new Pair<>("Register Application", new RegisterJobApplicationUI()::show));
		menuL.add(new Pair<>("Generate/export a template file for requirement specification", new GenerateRequirementSpecificationUI()::show));
		menuL.add(new Pair<>("Upload Requirements File", new ImportRequirementsFileUI()::show));
		menuL.add(new Pair<>("Enable/Disable Candidate Access", new EnableDisableCandidateUI()::show));

		return menuL;
	}

	private List<Pair<String, Action>> buildCustomerManagerMenu() {
		List<Pair<String, Action>> menuL = new ArrayList<>();

		menuL.add(new Pair<>("Register Customer", new RegisterCustomerUI()::show));
		menuL.add(new Pair<>("Register Job Opening", new RegisterJobOpeningUI()::show));
		menuL.add(new Pair<>("List Job Openings", new ListJobOpeningUI()::show));
		menuL.add(new Pair<>("List all applications for an Opening", new ListApplicationsByOpeningUI()::show));
		menuL.add(new Pair<>("Display all the personal Data if a Candidate", new DisplayCandidateDataUI()::show));
		menuL.add(new Pair<>("Setup the phases for a Job Opening", new SetUpPhasesJobOpeningUI()::show));
		menuL.add(new Pair<>("Select the Requirements Specification for a Job Opening", new SelectRequirementsUI() :: show));
		menuL.add(new Pair<>("Select the interview Model for a Job Opening", new SelectInterviewModelUI()::show));
		menuL.add(new Pair<>("Generate/export a template file for interviews", new GenerateInterviewModelUI()::show));
		menuL.add(new Pair<>("Open or closes Phase for Job Opening", new OpenClosePhasesJobOpeningUI()::show));
		menuL.add(new Pair<>("Rank Candidates of a JobOpening", new RankCandidateUI()::show));
		menuL.add(new Pair<>("Publish Results of the Selection", new PublishResultsUI()::show));
		menuL.add(new Pair<>("Record Interview with a Candidate", new RecordTimeDateForInterviewUI()::show));
		menuL.add(new Pair<>("Check the requirements of applications for a job opening", new ExecuteVerificationRequirementsJobOpeningUI()::show));
		menuL.add(new Pair<>(" Edit Job Opening ", new UpdateJobOpeningUI()::show));
		menuL.add(new Pair<>("Display the Data of a Job Application", new DisplayJobApplicationDataUI()::show));
		menuL.add(new Pair<>("Evaluate Interview For a Job Opening", new EvaluateInterviewsUI()::show));
		menuL.add(new Pair<>("Display list of candidates, using the job interview points", new OrderCandidatesByGradeUI()::show));
		menuL.add(new Pair<>("Upload Interview File", new ImportInterviewFileUI()::show));

		return menuL;
	}


	private List<Pair<String, Action>> buildLanguageEngineerMenu() {
		List<Pair<String, Action>> menuL = new ArrayList<>();

		menuL.add(new Pair<>("Deploy and configure a plugin", new ConfigurePluginUI()::show));

		return menuL;
	}


}
