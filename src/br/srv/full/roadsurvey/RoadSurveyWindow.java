// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RoadSurveyWindow.java

package br.srv.full.roadsurvey;

import java.awt.*;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.srv.full.roadsurvey.RoadSurveyManager;

// Referenced classes of package br.srv.full.roadsurvey:
//            RoadSurveyManager

public class RoadSurveyWindow extends JFrame {

	public RoadSurveyWindow() {
		jButtonAbrirProjeto = null;
		jButtonCopy = null;
		jButtonCut = null;
		jButtonFecharProjeto = null;
		jButtonforward = null;
		jButtonNovoProjeto = null;
		jButtonPast = null;
		jButtonPause = null;
		jButtonPlay = null;
		jButtonRefresh = null;
		jButtonRewind = null;
		jButtonStop = null;
		jContentPane = null;
		jJMenuBar = null;
		jLabelPlayer1 = null;
		jMenuAjuda = null;
		jMenuConfigurar = null;
		jMenuEditar = null;
		jMenuJanela = null;
		jMenuProjeto = null;
		jMenuVisualizar = null;
		jPanel = null;
		jPanel21 = null;
		jPanel211 = null;
		jPanel214 = null;
		jPanel5 = null;
		jPanelGeneral = null;
		jPanelInformations = null;
		jPanelMedia = null;
		jPanelMediaControl = null;
		jPanelToolBars = null;
		jTextField2 = null;
		jTextField4 = null;
		jTextField8 = null;
		jTextField9 = null;
		jTextFieldKm = null;
		jTextFieldLatitude = null;
		jTextFieldLongitude = null;
		jTextFieldPrecisao = null;
		jTextFieldVelocidade = null;
		jToolBar2 = null;
		jToolBar21 = null;
		jToolBar24 = null;
		jToolBar3 = null;
		jToolBarMediaControl = null;
		jSliderFrameRate = null;
		jSliderPlaybackRate = null;
		jSliderTime = null;
		players = new HashMap();
	}

	protected static ImageIcon createImageIcon(String iconName) {
		java.net.URL imgURL = RoadSurveyWindow.class
				.getResource((new StringBuilder("/images/")).append(iconName)
						.toString());
		ImageIcon icon;
		if (imgURL != null) {
			icon = new ImageIcon(imgURL);
		} else {
			System.err
					.println((new StringBuilder("Couldn't find file images/"))
							.append(iconName).append(" on images dir!")
							.toString());
			icon = null;
		}
		return icon;
	}

	private JButton getJButtonAbrirProjeto() {
		if (jButtonAbrirProjeto == null) {
			jButtonAbrirProjeto = new JButton();
			jButtonAbrirProjeto.setAction(getManager().getOpenProjectAction());
			jButtonAbrirProjeto.setBorder(null);
			jButtonAbrirProjeto.setToolTipText("Abrir um Projeto Existente");
			jButtonAbrirProjeto.setIcon(createImageIcon("folder_open.png"));
			jButtonAbrirProjeto.setPreferredSize(new Dimension(16, 16));
		}
		return jButtonAbrirProjeto;
	}

	private JButton getJButtonCopy() {
		if (jButtonCopy == null) {
			jButtonCopy = new JButton();
			jButtonCopy.setBorder(null);
			jButtonCopy.setToolTipText("Copiar");
			jButtonCopy.setIcon(createImageIcon("copy.png"));
			jButtonCopy.setPreferredSize(new Dimension(16, 16));
		}
		return jButtonCopy;
	}

	private JButton getJButtonCut() {
		if (jButtonCut == null) {
			jButtonCut = new JButton();
			jButtonCut.setToolTipText("Cortar");
			jButtonCut.setBorder(null);
			jButtonCut.setIcon(createImageIcon("cut.png"));
			jButtonCut.setPreferredSize(new Dimension(16, 16));
		}
		return jButtonCut;
	}

	private JButton getJButtonFecharProjeto() {
		if (jButtonFecharProjeto == null) {
			jButtonFecharProjeto = new JButton();
			jButtonFecharProjeto.setBorder(null);
			jButtonFecharProjeto.setToolTipText("Fechar o Projeto Aberto");
			jButtonFecharProjeto.setIcon(createImageIcon("folder_close.png"));
			jButtonFecharProjeto.setPreferredSize(new Dimension(16, 16));
		}
		return jButtonFecharProjeto;
	}

	private JButton getJButtonforward() {
		if (jButtonforward == null) {
			jButtonforward = new JButton();
			jButtonforward.setBorder(null);
			jButtonforward.setToolTipText("Adiantar Levantamento");
			jButtonforward.setIcon(createImageIcon("fast_forward.png"));
			jButtonforward.setPreferredSize(new Dimension(16, 16));
		}
		return jButtonforward;
	}

	private JButton getJButtonNovoProjeto() {
		if (jButtonNovoProjeto == null) {
			jButtonNovoProjeto = new JButton();
			jButtonNovoProjeto.setBorder(null);
			jButtonNovoProjeto.setToolTipText("Criar um Novo Projeto");
			jButtonNovoProjeto.setIcon(createImageIcon("new.png"));
			jButtonNovoProjeto.setPreferredSize(new Dimension(16, 16));
		}
		return jButtonNovoProjeto;
	}

	private JButton getJButtonPast() {
		if (jButtonPast == null) {
			jButtonPast = new JButton();
			jButtonPast.setToolTipText("Colar");
			jButtonPast.setBorder(null);
			jButtonPast.setIcon(createImageIcon("paste.png"));
			jButtonPast.setPreferredSize(new Dimension(16, 16));
		}
		return jButtonPast;
	}

	private JButton getJButtonPause() {
		if (jButtonPause == null) {
			jButtonPause = new JButton();
			jButtonPause.setBorder(null);
			jButtonPause.setToolTipText("Pausar Levantamento");
			jButtonPause.setIcon(createImageIcon("pause.png"));
			jButtonPause.setPreferredSize(new Dimension(16, 16));
		}
		return jButtonPause;
	}

	private JButton getJButtonPlay() {
		if (jButtonPlay == null) {
			jButtonPlay = new JButton();
			jButtonPlay.setAction(getManager().getMediaStart());
			jButtonPlay.setBorder(null);
			jButtonPlay.setToolTipText("Iniciar Levantamento");
			jButtonPlay.setIcon(createImageIcon("play.png"));
			jButtonPlay.setPreferredSize(new Dimension(16, 16));
		}
		return jButtonPlay;
	}

	private JButton getJButtonRefresh() {
		if (jButtonRefresh == null) {
			jButtonRefresh = new JButton();
			jButtonRefresh.setBorder(null);
			jButtonRefresh
					.setToolTipText("Recarregar o Projeto Aberto e descartar altera\uFFFD\uFFFDes");
			jButtonRefresh.setIcon(createImageIcon("refresh.png"));
			jButtonRefresh.setPreferredSize(new Dimension(16, 16));
		}
		return jButtonRefresh;
	}

	private JButton getJButtonRewind() {
		if (jButtonRewind == null) {
			jButtonRewind = new JButton();
			jButtonRewind.setBorder(null);
			jButtonRewind.setToolTipText("Voltar Levantamento");
			jButtonRewind.setIcon(createImageIcon("fast_rewind.png"));
			jButtonRewind.setPreferredSize(new Dimension(16, 16));
		}
		return jButtonRewind;
	}

	private JButton getJButtonStop() {
		if (jButtonStop == null) {
			jButtonStop = new JButton();
			jButtonStop.setAction(getManager().getMediaStopAction());
			jButtonStop.setBorder(null);
			jButtonStop.setToolTipText("Parar Levantamento");
			jButtonStop.setIcon(createImageIcon("stop.png"));
			jButtonStop.setPreferredSize(new Dimension(16, 16));
		}
		return jButtonStop;
	}

	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BoxLayout(getJContentPane(), 1));
			jContentPane.add(getJPanel(), null);
			jContentPane.add(getJPanelGeneral(), null);
		}
		return jContentPane;
	}

	private JMenuBar getJJMenuBar() {
		if (jJMenuBar == null) {
			jJMenuBar = new JMenuBar();
			jJMenuBar.add(getJMenuProjeto());
			jJMenuBar.add(getJMenuEditar());
			jJMenuBar.add(getJMenuVisualizar());
			jJMenuBar.add(getJMenuConfigurar());
			jJMenuBar.add(getJMenuJanela());
			jJMenuBar.add(getJMenuAjuda());
		}
		return jJMenuBar;
	}

	private JMenu getJMenuAjuda() {
		if (jMenuAjuda == null) {
			jMenuAjuda = new JMenu();
			jMenuAjuda.setText("Ajuda");
		}
		return jMenuAjuda;
	}

	private JMenu getJMenuConfigurar() {
		if (jMenuConfigurar == null) {
			jMenuConfigurar = new JMenu();
			jMenuConfigurar.setText("Configurar");
		}
		return jMenuConfigurar;
	}

	private JMenu getJMenuEditar() {
		if (jMenuEditar == null) {
			jMenuEditar = new JMenu();
			jMenuEditar.setText("Editar");
		}
		return jMenuEditar;
	}

	private JMenu getJMenuJanela() {
		if (jMenuJanela == null) {
			jMenuJanela = new JMenu();
			jMenuJanela.setText("Janela");
		}
		return jMenuJanela;
	}

	private JMenu getJMenuProjeto() {
		if (jMenuProjeto == null) {
			jMenuProjeto = new JMenu();
			jMenuProjeto.setText("Projeto");
		}
		return jMenuProjeto;
	}

	private JMenu getJMenuVisualizar() {
		if (jMenuVisualizar == null) {
			jMenuVisualizar = new JMenu();
			jMenuVisualizar.setText("Visualizar");
		}
		return jMenuVisualizar;
	}

	private JPanel getJPanel() {
		if (jPanel == null) {
			GridLayout gridLayout1 = new GridLayout();
			gridLayout1.setRows(2);
			gridLayout1.setVgap(1);
			gridLayout1.setHgap(1);
			jPanel = new JPanel();
			jPanel.setLayout(gridLayout1);
			jPanel.add(getJPanelToolBars(), null);
			jPanel.add(getJPanelInformations(), null);
		}
		return jPanel;
	}

	private JPanel getJPanel21() {
		if (jPanel21 == null) {
			GridLayout gridLayout3 = new GridLayout();
			gridLayout3.setRows(1);
			gridLayout3.setHgap(2);
			jPanel21 = new JPanel();
			jPanel21.setLayout(gridLayout3);
			jPanel21.add(getJButtonNovoProjeto(), null);
			jPanel21.add(getJButtonAbrirProjeto(), null);
			jPanel21.add(getJButtonFecharProjeto(), null);
			jPanel21.add(getJButtonRefresh(), null);
		}
		return jPanel21;
	}

	private JPanel getJPanel211() {
		if (jPanel211 == null) {
			GridLayout gridLayout31 = new GridLayout();
			gridLayout31.setRows(1);
			gridLayout31.setHgap(2);
			jPanel211 = new JPanel();
			jPanel211.setLayout(gridLayout31);
			jPanel211.add(getJButtonCopy(), null);
			jPanel211.add(getJButtonCut(), null);
			jPanel211.add(getJButtonPast(), null);
		}
		return jPanel211;
	}

	private JPanel getJPanel214() {
		if (jPanel214 == null) {
			GridLayout gridLayout34 = new GridLayout();
			gridLayout34.setRows(1);
			gridLayout34.setHgap(2);
			jPanel214 = new JPanel();
			jPanel214.setLayout(gridLayout34);
			jPanel214.add(getJTextField8(), null);
			jPanel214.add(getJTextField9(), null);
		}
		return jPanel214;
	}

	private JPanel getJPanel5() {
		if (jPanel5 == null) {
			GridLayout gridLayout2 = new GridLayout();
			gridLayout2.setRows(1);
			jPanel5 = new JPanel();
			jPanel5.setLayout(gridLayout2);
			jPanel5.add(getJTextFieldLatitude(), null);
			jPanel5.add(getJTextFieldLongitude(), null);
			jPanel5.add(getJTextField2(), null);
			jPanel5.add(getJTextFieldPrecisao(), null);
			jPanel5.add(getJTextField4(), null);
			jPanel5.add(getJTextFieldKm(), null);
			jPanel5.add(getJTextFieldVelocidade(), null);
		}
		return jPanel5;
	}

	private JPanel getJPanelGeneral() {
		if (jPanelGeneral == null) {
			GridBagConstraints gridBagConstraints14 = new GridBagConstraints();
			gridBagConstraints14.fill = 2;
			gridBagConstraints14.gridy = 2;
			gridBagConstraints14.weightx = 1.0D;
			gridBagConstraints14.gridx = 1;
			GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
			gridBagConstraints13.gridx = 0;
			gridBagConstraints13.fill = 3;
			GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
			gridBagConstraints12.gridx = 0;
			gridBagConstraints12.fill = 3;
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.fill = 1;
			gridBagConstraints11.weighty = 1.0D;
			gridBagConstraints11.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints11.gridheight = 2;
			gridBagConstraints11.weightx = 1.0D;
			jPanelGeneral = new JPanel();
			jPanelGeneral.setLayout(new GridBagLayout());
			jPanelGeneral.add(getJSliderPlaybackRate(), gridBagConstraints13);
			jPanelGeneral.add(getJSliderFrameRate(), gridBagConstraints12);
			jPanelGeneral.add(getJPanelMedia(), gridBagConstraints11);
			jPanelGeneral.add(getJSliderTime(), gridBagConstraints14);
		}
		return jPanelGeneral;
	}

	private JPanel getJPanelInformations() {
		if (jPanelInformations == null) {
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.anchor = 17;
			gridBagConstraints1.gridx = -1;
			gridBagConstraints1.gridy = -1;
			gridBagConstraints1.weightx = 1.0D;
			gridBagConstraints1.weighty = 0.0D;
			gridBagConstraints1.fill = 1;
			jPanelInformations = new JPanel();
			jPanelInformations.setLayout(new GridBagLayout());
			jPanelInformations.add(getJToolBar3(), gridBagConstraints1);
		}
		return jPanelInformations;
	}

	private JPanel getJPanelMedia() {
		if (jPanelMedia == null) {
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.weightx = 1.0D;
			gridBagConstraints5.weighty = 1.0D;
			jLabelPlayer1 = new JLabel();
			jLabelPlayer1.setText("Player 1");
			jPanelMedia = new JPanel();
			jPanelMedia.setLayout(new GridBagLayout());
			jPanelMedia.add(jLabelPlayer1, gridBagConstraints5);
		}
		return jPanelMedia;
	}

	private JPanel getJPanelMediaControl() {
		if (jPanelMediaControl == null) {
			GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
			gridBagConstraints10.insets = new Insets(0, 1, 0, 1);
			gridBagConstraints10.gridy = 0;
			gridBagConstraints10.gridx = 4;
			GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
			gridBagConstraints9.insets = new Insets(0, 1, 0, 1);
			gridBagConstraints9.gridy = 0;
			gridBagConstraints9.gridx = 3;
			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
			gridBagConstraints8.gridy = 0;
			gridBagConstraints8.insets = new Insets(0, 1, 0, 1);
			gridBagConstraints8.gridx = 2;
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			gridBagConstraints7.gridy = 0;
			gridBagConstraints7.insets = new Insets(0, 1, 0, 1);
			gridBagConstraints7.gridx = 1;
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.gridy = 0;
			gridBagConstraints6.insets = new Insets(0, 1, 0, 1);
			gridBagConstraints6.gridx = 0;
			jPanelMediaControl = new JPanel();
			jPanelMediaControl.setLayout(new GridBagLayout());
			jPanelMediaControl.add(getJButtonStop(), gridBagConstraints6);
			jPanelMediaControl.add(getJButtonRewind(), gridBagConstraints7);
			jPanelMediaControl.add(getJButtonPause(), gridBagConstraints8);
			jPanelMediaControl.add(getJButtonPlay(), gridBagConstraints9);
			jPanelMediaControl.add(getJButtonforward(), gridBagConstraints10);
		}
		return jPanelMediaControl;
	}

	private JPanel getJPanelToolBars() {
		if (jPanelToolBars == null) {
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setHgap(5);
			flowLayout.setAlignment(0);
			flowLayout.setVgap(1);
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.fill = 3;
			gridBagConstraints4.weightx = 1.0D;
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.anchor = 17;
			gridBagConstraints3.gridx = -1;
			gridBagConstraints3.gridy = -1;
			gridBagConstraints3.weightx = 1.0D;
			gridBagConstraints3.fill = 1;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.fill = 3;
			gridBagConstraints2.anchor = 17;
			gridBagConstraints2.weightx = 1.0D;
			jPanelToolBars = new JPanel();
			jPanelToolBars.setLayout(flowLayout);
			jPanelToolBars.add(getJToolBar2(), null);
			jPanelToolBars.add(getJToolBar21(), null);
			jPanelToolBars.add(getJToolBarMediaControl(), null);
			jPanelToolBars.add(getJToolBar24(), null);
		}
		return jPanelToolBars;
	}

	private JTextField getJTextField2() {
		if (jTextField2 == null) {
			jTextField2 = new JFormattedTextField();
			jTextField2.setHorizontalAlignment(0);
			jTextField2.setColumns(15);
			jTextField2.setEditable(false);
			jTextField2.setText("ALTITUDE");
		}
		return jTextField2;
	}

	private JTextField getJTextField4() {
		if (jTextField4 == null)
			jTextField4 = new JTextField();
		return jTextField4;
	}

	private JTextField getJTextField8() {
		if (jTextField8 == null) {
			jTextField8 = new JTextField();
			jTextField8.setColumns(10);
		}
		return jTextField8;
	}

	private JTextField getJTextField9() {
		if (jTextField9 == null) {
			jTextField9 = new JTextField();
			jTextField9.setColumns(10);
		}
		return jTextField9;
	}

	private JTextField getJTextFieldKm() {
		if (jTextFieldKm == null) {
			jTextFieldKm = new JFormattedTextField();
			jTextFieldKm.setHorizontalAlignment(0);
			jTextFieldKm.setColumns(15);
			jTextFieldKm.setEditable(false);
			jTextFieldKm.setText("KM");
		}
		return jTextFieldKm;
	}

	private JTextField getJTextFieldLatitude() {
		if (jTextFieldLatitude == null) {
			jTextFieldLatitude = new JFormattedTextField();
			jTextFieldLatitude.setHorizontalAlignment(0);
			jTextFieldLatitude.setColumns(15);
			jTextFieldLatitude.setEditable(false);
			jTextFieldLatitude.setText("LATITUDE");
		}
		return jTextFieldLatitude;
	}

	private JTextField getJTextFieldLongitude() {
		if (jTextFieldLongitude == null) {
			jTextFieldLongitude = new JFormattedTextField();
			jTextFieldLongitude.setText("LONGITUDE");
			jTextFieldLongitude.setColumns(15);
			jTextFieldLongitude.setEditable(false);
			jTextFieldLongitude.setHorizontalAlignment(0);
		}
		return jTextFieldLongitude;
	}

	private JTextField getJTextFieldPrecisao() {
		if (jTextFieldPrecisao == null) {
			jTextFieldPrecisao = new JFormattedTextField();
			jTextFieldPrecisao.setColumns(15);
			jTextFieldPrecisao.setHorizontalAlignment(0);
			jTextFieldPrecisao.setEditable(false);
			jTextFieldPrecisao.setText("PRECIS\303O");
		}
		return jTextFieldPrecisao;
	}

	private JTextField getJTextFieldVelocidade() {
		if (jTextFieldVelocidade == null) {
			jTextFieldVelocidade = new JFormattedTextField();
			jTextFieldVelocidade.setColumns(15);
			jTextFieldVelocidade.setText("VELOCIDADE");
			jTextFieldVelocidade.setEditable(false);
			jTextFieldVelocidade.setHorizontalAlignment(0);
		}
		return jTextFieldVelocidade;
	}

	private JToolBar getJToolBar2() {
		if (jToolBar2 == null) {
			jToolBar2 = new JToolBar();
			jToolBar2.setFloatable(false);
			jToolBar2.add(getJPanel21());
		}
		return jToolBar2;
	}

	private JToolBar getJToolBar21() {
		if (jToolBar21 == null) {
			jToolBar21 = new JToolBar();
			jToolBar21.setFloatable(false);
			jToolBar21.add(getJPanel211());
		}
		return jToolBar21;
	}

	private JToolBar getJToolBar24() {
		if (jToolBar24 == null) {
			jToolBar24 = new JToolBar();
			jToolBar24.add(getJPanel214());
		}
		return jToolBar24;
	}

	private JToolBar getJToolBar3() {
		if (jToolBar3 == null) {
			jToolBar3 = new JToolBar();
			jToolBar3.setFloatable(false);
			jToolBar3.add(getJPanel5());
		}
		return jToolBar3;
	}

	private JToolBar getJToolBarMediaControl() {
		if (jToolBarMediaControl == null) {
			jToolBarMediaControl = new JToolBar();
			jToolBarMediaControl.setFloatable(false);
			jToolBarMediaControl.add(getJPanelMediaControl());
		}
		return jToolBarMediaControl;
	}

	private RoadSurveyManager getManager() {
		return rsm;
	}

	void initialize() {
		setJMenuBar(getJJMenuBar());
		setContentPane(getJContentPane());
		Dimension size = new Dimension(1050, 700);
		setPreferredSize(size);
		setSize(size);
		setResizable(false);
		setTitle("Hiper Video Road Survey");
		setEnabled(true);
		setDefaultCloseOperation(3);
		addWindowListener(getManager().getWindowListener());
		setLocationRelativeTo(null);
		initialized = true;
	}

	public void setManager(RoadSurveyManager p_rsm) {
		if (rsm == null)
			rsm = p_rsm;
		else
			throw new RuntimeException((new StringBuilder(
					String.valueOf(RoadSurveyManager.class.getName()))).append(
					" Já registrado!").toString());
	}

	public void addPlayer(Component l_visual, String l_namePlayer[]) {
		log.info("Adicionando um novo Componente de Video!");
		log.trace((new StringBuilder("Adicionado o Componente de Video: "))
				.append(l_visual.getClass()).toString());
		JPanel l_panel = getJPanelMedia();
		getPlayers().put(l_visual, l_panel);
		l_panel.removeAll();
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = 1;
		gridBagConstraints.gridy = -1;
		gridBagConstraints.weightx = 1.0D;
		gridBagConstraints.weighty = 1.0D;
		gridBagConstraints.gridx = -1;
		l_panel.add(l_visual, gridBagConstraints);
		l_panel.validate();
	}

	public void setVisible(boolean b) {
		if (!initialized)
			initialize();
		super.setVisible(b);
	}

	public static RoadSurveyWindow getInstance() {
		if (instance == null)
			instance = new RoadSurveyWindow();
		return instance;
	}

	private JSlider getJSliderFrameRate() {
		if (jSliderFrameRate == null) {
			jSliderFrameRate = new JSlider();
			jSliderFrameRate.setOrientation(1);
			jSliderFrameRate.setPaintTicks(true);
			jSliderFrameRate.setPaintTrack(false);
			jSliderFrameRate.setMaximum(30);
			jSliderFrameRate.setMajorTickSpacing(5);
			jSliderFrameRate.setMinorTickSpacing(1);
			jSliderFrameRate.setValue(0);
			jSliderFrameRate.setMinimum(-10);
		}
		return jSliderFrameRate;
	}

	private JSlider getJSliderPlaybackRate() {
		if (jSliderPlaybackRate == null) {
			jSliderPlaybackRate = new JSlider();
			jSliderPlaybackRate.setOrientation(1);
			jSliderPlaybackRate.setMajorTickSpacing(1);
			jSliderPlaybackRate.setPaintTicks(true);
			jSliderPlaybackRate.setPaintTrack(false);
			jSliderPlaybackRate.setModel(getManager()
					.getMediaPlaybackRateSliderModel());
		}
		return jSliderPlaybackRate;
	}

	private JSlider getJSliderTime() {
		if (jSliderTime == null) {
			jSliderTime = new JSlider();
			jSliderTime.setMaximum(0x5f5e100);
			jSliderTime.setMajorTickSpacing(10);
			jSliderTime.setMinorTickSpacing(1);
			jSliderTime.setModel(getManager().getMediaTimeSliderModel());
		}
		return jSliderTime;
	}

	public void removePlayer(Component l_visual) {
		Map l_players = getPlayers();
		JPanel l_panel = (JPanel) l_players.get(l_visual);
		l_panel.removeAll();
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = 1;
		gridBagConstraints.gridy = -1;
		gridBagConstraints.weightx = 1.0D;
		gridBagConstraints.weighty = 1.0D;
		gridBagConstraints.gridx = -1;
		l_panel.add(new JLabel("Player 1"), gridBagConstraints);
		l_panel.validate();
		l_players.remove(l_visual);
	}

	private Map getPlayers() {
		return players;
	}

	private static final long serialVersionUID = 0x4c67e85dacad2da8L;
	private static RoadSurveyWindow instance;
	private boolean initialized;
	private JButton jButtonAbrirProjeto;
	private JButton jButtonCopy;
	private JButton jButtonCut;
	private JButton jButtonFecharProjeto;
	private JButton jButtonforward;
	private JButton jButtonNovoProjeto;
	private JButton jButtonPast;
	private JButton jButtonPause;
	private JButton jButtonPlay;
	private JButton jButtonRefresh;
	private JButton jButtonRewind;
	private JButton jButtonStop;
	private JPanel jContentPane;
	private JMenuBar jJMenuBar;
	private JLabel jLabelPlayer1;
	private JMenu jMenuAjuda;
	private JMenu jMenuConfigurar;
	private JMenu jMenuEditar;
	private JMenu jMenuJanela;
	private JMenu jMenuProjeto;
	private JMenu jMenuVisualizar;
	private JPanel jPanel;
	private JPanel jPanel21;
	private JPanel jPanel211;
	private JPanel jPanel214;
	private JPanel jPanel5;
	private JPanel jPanelGeneral;
	private JPanel jPanelInformations;
	private JPanel jPanelMedia;
	private JPanel jPanelMediaControl;
	private JPanel jPanelToolBars;
	private JTextField jTextField2;
	private JTextField jTextField4;
	private JTextField jTextField8;
	private JTextField jTextField9;
	private JTextField jTextFieldKm;
	private JTextField jTextFieldLatitude;
	private JTextField jTextFieldLongitude;
	private JTextField jTextFieldPrecisao;
	private JTextField jTextFieldVelocidade;
	private JToolBar jToolBar2;
	private JToolBar jToolBar21;
	private JToolBar jToolBar24;
	private JToolBar jToolBar3;
	private JToolBar jToolBarMediaControl;
	private final Log log = LogFactory.getLog(getClass());
	private RoadSurveyManager rsm;
	private JSlider jSliderFrameRate;
	private JSlider jSliderPlaybackRate;
	private JSlider jSliderTime;
	private Map players;
}
