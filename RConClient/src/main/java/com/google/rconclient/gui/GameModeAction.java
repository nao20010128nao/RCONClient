/**
 * 
 */
package com.google.rconclient.gui;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.AbstractAction;
import javax.swing.JList;

import com.google.rconclient.rcon.AuthenticationException;
import com.google.rconclient.rcon.GameMode;
import com.google.rconclient.rcon.RCon;

/**
 * The action to be used to change the game mode for a player.
 * 
 * @author vincent
 */
public class GameModeAction extends AbstractAction {

	/**
	 * The serial version id.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The resource bundle of the messages.
	 */
	private static final ResourceBundle MESSAGES = new Messages(GameModeAction.class);

	private static final String MSG_CLASS_NAME = GameModeAction.class.getSimpleName();
	private static final String MSG_NAME = MSG_CLASS_NAME + ".name";
	private static final String MSG_MNEMONIC = MSG_CLASS_NAME + ".mnemonic";

	/**
	 * The container with the global objects.
	 */
	private final Globals globals;

	/**
	 * The list with the selected items.
	 */
	private final JList<String> list;

	/**
	 * A new action will be created.
	 * 
	 * @param globals
	 *            The container with the global objects.
	 * @param list
	 *            The list with the selected items.
	 */
	public GameModeAction(final Globals globals, final JList<String> list) {
		super();
		this.globals = globals;
		this.list = list;

		putValue(NAME, MESSAGES.getString(MSG_NAME));
		putValue(MNEMONIC_KEY, KeyEventUtil.getKeyCode(MESSAGES.getString(MSG_MNEMONIC)));
	}

	@Override
	public void actionPerformed(final ActionEvent event) {
		final RCon connection = globals.getConnection();
		final List<String> selectedPlayers = list.getSelectedValuesList();
		list.clearSelection();
		try {
			// TODO The game mode needs to be asked by a dialog.
			final GameMode gameMode = GameMode.Survival;
			for (final String player : selectedPlayers) {
				if (connection != null) {
					connection.gameMode(player, gameMode);
				}
			}
			globals.refreshConnection(connection);
		} catch (AuthenticationException | IOException e) {
			globals.setConnection(null);
		}
	}

}
