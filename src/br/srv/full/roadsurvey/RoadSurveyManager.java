// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RoadSurveyManager.java

package br.srv.full.roadsurvey;

import br.srv.full.roadsurvey.models.MediaPlaybackRateSliderModel;
import br.srv.full.roadsurvey.models.MediaTimeSliderModel;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.media.*;
import javax.media.protocol.DataSource;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
  
public class RoadSurveyManager
    implements Runnable
{
    public class MediaPlaybackRateSliderModelListener
        implements ChangeListener
    {

        public void stateChanged(ChangeEvent e)
        {
            log.trace(e);
            ajustRateWithSliderPlaybackRate();
        }

        private final Log log = LogFactory.getLog(getClass());
        final RoadSurveyManager rsm;

        public MediaPlaybackRateSliderModelListener()
        {

            super();
            rsm = RoadSurveyManager.this;
        }
    }

    private class MediaPlayerControllerListener extends ControllerAdapter
    {

        public void configureComplete(ConfigureCompleteEvent e)
        {
            log.info(e);
            log.debug((new StringBuilder("Fonte: ")).append(e.getSource()).toString());
            debugStartLatency();
        }

        private void debugStartLatency()
        {
            Time l_latency = getMediaProcessor().getStartLatency();
            if(Player.LATENCY_UNKNOWN.equals(l_latency))
                log.debug("Latency is Unknow");
            else
                log.debug((new StringBuilder("Start Latency: ")).append(l_latency.getSeconds()).toString());
        }

        public void endOfMedia(EndOfMediaEvent e)
        {
            log.info(e);
            log.debug((new StringBuilder("Fonte: ")).append(e.getSource()).toString());
            Controller controller = (Controller)e.getSource();
            controller.stop();
            controller.setMediaTime(new Time(0L));
            controller.deallocate();
        }

        public void prefetchComplete(PrefetchCompleteEvent e)
        {
            log.info(e);
            log.debug((new StringBuilder("Fonte: ")).append(e.getSource()).toString());
            debugStartLatency();
        }

        public void realizeComplete(RealizeCompleteEvent e)
        {
            log.info(e);
            Object l_source = e.getSource();
            log.debug((new StringBuilder("Fonte: ")).append(l_source).toString());
            if(hasMediaProcessor())
            {
                debugStartLatency();
                log.info("Obtendo O componente de video do Processador de Media para adicionar a Janela!");
                Player l_player = (Player)l_source;
                getView().addPlayer(l_player.getVisualComponent(), new String[0]);
                ajustRateWithSliderPlaybackRate();
            }
        }

        private final Log log;
        final RoadSurveyManager rsm;

        private MediaPlayerControllerListener()
        {
            super();
            rsm = RoadSurveyManager.this;
            log = LogFactory.getLog(getClass());
        }

        MediaPlayerControllerListener(MediaPlayerControllerListener mediaplayercontrollerlistener)
        {
            this();
        }
    }

    private class MediaStartAction extends AbstractMediaAction
    {

        public void actionPerformed(ActionEvent e)
        {
            log.info(e);
            if(!hasProjectSelected())
            {
                log.info("N\343o existe um projeto selecionado!");
                getOpenProjectAction().actionPerformed(e);
            }
            DataSource l_ds = createDataSource();
            Player l_mp;
            try
            {
                l_mp = getMediaProcessor(l_ds);
            }
            catch(NoPlayerException e1)
            {
                log.warn(e1);
                throw new RuntimeException(e1);
            }
            catch(IOException e1)
            {
                log.warn(e1);
                throw new RuntimeException(e1);
            }
            log.info("Iniciando o Processador de Midia!");
            l_mp.start();
            log.info((new StringBuilder("fim ")).append(e).toString());
        }

        private DataSource createDataSource()
        {
            URI l_project = getProjectSelected();
            String l_media = l_project.toString();
            log.info((new StringBuilder("Projeto Selecionado: ")).append(l_media).toString());
            log.trace("Criando o um Localizador de Media!");
            MediaLocator l_ml = new MediaLocator(l_media);
            DataSource l_ds = null;
            try
            {
                l_ds = Manager.createDataSource(l_ml);
            }
            catch(NoDataSourceException e1)
            {
                log.warn(e1);
            }
            catch(IOException e1)
            {
                log.warn(e1);
                throw new RuntimeException(e1);
            }
            return l_ds;
        }

        private static final long serialVersionUID = 0xe4d92782f5e447a2L;
        final RoadSurveyManager rsm;

        private MediaStartAction()
        {
        	super();
        	rsm = RoadSurveyManager.this;
            
        }

        MediaStartAction(MediaStartAction mediastartaction)
        {
            this();
        }
    }

    private class MediaStopAction extends AbstractMediaAction
    {

        public void actionPerformed(ActionEvent e)
        {
            log.info(e);
            Player l_player = (Player)e;
            l_player.stop();
            java.awt.Component l_visual = l_player.getVisualComponent();
            getView().removePlayer(l_visual);
        }

        private static final long serialVersionUID = 0xf512ff285851506aL;
        final RoadSurveyManager rsm;

        private MediaStopAction()
        {
        	super();
        	rsm = RoadSurveyManager.this;
            
        }

        MediaStopAction(MediaStopAction mediastopaction)
        {
            this();
        }
    }

    public class OpenProjectAction extends AbstractMediaAction
    {

        public void actionPerformed(ActionEvent e)
        {
            log.info(e);
            JFileChooser jc = new JFileChooser();
            jc.setFileSelectionMode(0);
            do
            {
                int r = jc.showOpenDialog(getView());
                log.info(Integer.valueOf(r));
                switch(r)
                {
                case 0: // '\0'
                    log.info("Open");
                    File l_file = jc.getSelectedFile();
                    log.info(l_file);
                    if(l_file.exists() && l_file.canRead())
                    {
                        try
                        {
                            setProjectSelected(l_file.toURI());
                        }
                        catch(ProjectRootPathNotAllowedExcpetion e1)
                        {
                            log.warn(e1);
                        }
                        return;
                    }
                    break;

                case 1: // '\001'
                    log.info("Cancel");
                    try
                    {
                        try
                        {
                            setProjectSelected(new URI("blueline://null/"));
                        }
                        catch(ProjectRootPathNotAllowedExcpetion e1)
                        {
                            log.warn(e1);
                        }
                        return;
                    }
                    catch(URISyntaxException e1)
                    {
                        log.warn(e1);
                    }
                    // fall through

                default:
                    log.info(Integer.valueOf(r));
                    break;
                }
            } while(true);
        }

        private static final long serialVersionUID = 0xd227af4446859bb7L;
        final RoadSurveyManager rsm;

        public OpenProjectAction()
        {
        	super();
        	rsm = RoadSurveyManager.this;
            
        }
    }

    private class RoadSurveyWindowListener extends WindowAdapter
    {

        public void windowClosing(WindowEvent e)
        {
            log.info("Fechando a Janela!");
            if(hasMediaProcessor())
            {
                log.trace("Removendo o Processador de Media!");
                Player l_processor = getMediaProcessor();
                log.info("Interrompendo o Processador de Media!");
                l_processor.stop();
                log.info("Desalocando recursos do Processador de Media!");
                l_processor.deallocate();
            }
        }

        private final Log log;
        final RoadSurveyManager rsm;

        private RoadSurveyWindowListener()
        {
        	super();
        	rsm = RoadSurveyManager.this;
            
            log = LogFactory.getLog(getClass());
        }

        RoadSurveyWindowListener(RoadSurveyWindowListener roadsurveywindowlistener)
        {
            this();
        }
    }


    public RoadSurveyManager()
    {
    }

    public static RoadSurveyManager getInstance()
    {
        if(instance == null)
            instance = new RoadSurveyManager();
        return instance;
    }

    private void ajustRateWithSliderPlaybackRate()
    {
        BoundedRangeModel l_model = getMediaPlaybackRateSliderModel();
        if(!l_model.getValueIsAdjusting())
        {
            int l_value = l_model.getValue();
            log.debug(Integer.valueOf(l_value));
            if(hasMediaProcessor())
            {
                float i = l_value;
                if(i < 1.0F)
                    i--;
                log.info((new StringBuilder("Ajustando o Playback Rate para: ")).append(i).toString());
                getMediaProcessor().setRate(i);
            }
        }
    }

    public BoundedRangeModel getMediaPlaybackRateSliderModel()
    {
        if(mediaPlaybackRateSliderModel == null)
        {
            mediaPlaybackRateSliderModel = new MediaPlaybackRateSliderModel();
            mediaPlaybackRateSliderModel.addChangeListener(getMediaPlaybackRateSliderModelListener());
        }
        return mediaPlaybackRateSliderModel;
    }

    private ChangeListener getMediaPlaybackRateSliderModelListener()
    {
        if(mediaPlaybackRateSliderModelListener == null)
            mediaPlaybackRateSliderModelListener = new MediaPlaybackRateSliderModelListener();
        return mediaPlaybackRateSliderModelListener;
    }

    private ControllerListener getMediaPlayerControllerListener()
    {
        if(mediaPlayerControllerListener == null)
            mediaPlayerControllerListener = new MediaPlayerControllerListener(null);
        return mediaPlayerControllerListener;
    }

    public Player getMediaProcessor()
    {
        return mediaProcessor;
    }

    private Player getMediaProcessor(DataSource p_ds)
        throws NoPlayerException, IOException
    {
        log.trace((new StringBuilder("Criando um Processador de media com o DataSource: ")).append(p_ds).toString());
        mediaProcessor = Manager.createPlayer(p_ds);
        log.trace("Adicionado o Ouvinte de Listener!");
        mediaProcessor.addControllerListener(getMediaPlayerControllerListener());
        return mediaProcessor;
    }

    public Action getMediaStart()
    {
        if(mediaStartAction == null)
            mediaStartAction = new MediaStartAction(null);
        return mediaStartAction;
    }

    public Action getMediaStopAction()
    {
        if(mediaStopAction == null)
            mediaStopAction = new MediaStopAction(null);
        return mediaStopAction;
    }

    public BoundedRangeModel getMediaTimeSliderModel()
    {
        if(mediaTimeSliderModel == null)
            mediaTimeSliderModel = new MediaTimeSliderModel();
        return mediaTimeSliderModel;
    }

    public Action getOpenProjectAction()
    {
        if(openProjectAction == null)
            openProjectAction = new OpenProjectAction();
        return openProjectAction;
    }

    public URI getProjectSelected()
    {
        return projectSelected;
    }

    public RoadSurveyWindow getView()
    {
        return rsw;
    }

    public WindowListener getWindowListener()
    {
        if(windowListener == null)
            windowListener = new RoadSurveyWindowListener(null);
        return windowListener;
    }

    public boolean hasMediaProcessor()
    {
        return mediaProcessor != null;
    }

    public boolean hasProjectSelected()
    {
        return projectSelected != null;
    }

    public void run()
    {
        rsw.pack();
        rsw.setVisible(true);
    }

    public void setProjectSelected(URI uri)
        throws ProjectRootPathNotAllowedExcpetion
    {
        log.info(uri);
        log.debug((new StringBuilder("Authority: ")).append(uri.getAuthority()).toString());
        log.debug((new StringBuilder("Fragment: ")).append(uri.getFragment()).toString());
        log.debug((new StringBuilder("Host: ")).append(uri.getHost()).toString());
        String l_path = uri.getPath();
        log.debug((new StringBuilder("Path: ")).append(l_path).toString());
        log.debug((new StringBuilder("Port: ")).append(uri.getPort()).toString());
        log.debug((new StringBuilder("Query: ")).append(uri.getQuery()).toString());
        log.debug((new StringBuilder("Scheme: ")).append(uri.getScheme()).toString());
        log.debug((new StringBuilder("User Infor: ")).append(uri.getUserInfo()).toString());
        if("blueline://null/".equals(uri.toString()) || l_path.toLowerCase().endsWith(".prj"))
        {
            projectSelected = uri;
        } else
        {
            if(l_path.isEmpty() || l_path.endsWith("/"))
                throw new ProjectRootPathNotAllowedExcpetion(uri);
            if(l_path.matches("(.)*\\.(.){3}$"))
            {
                l_path = l_path.replaceAll("\\.(.){3}$", ".prj");
                log.info(l_path);
                try
                {
                    uri = new URI(uri.getScheme(), uri.getUserInfo(), uri.getHost(), uri.getPort(), l_path, uri.getQuery(), uri.getFragment());
                    log.debug(uri);
                    projectSelected = uri;
                }
                catch(URISyntaxException e)
                {
                    log.warn(e);
                }
            }
        }
    }

    public void setView(RoadSurveyWindow p_rsw)
    {
        rsw = p_rsw;
        rsw.setManager(this);
    }

    public void start()
    {
        SwingUtilities.invokeLater(this);
    }

    private static final String PROJECT_NOT_SELECTED = "blueline://null/";
    private static RoadSurveyManager instance;
    private final Log log = LogFactory.getLog(getClass());
    private BoundedRangeModel mediaPlaybackRateSliderModel;
    private ChangeListener mediaPlaybackRateSliderModelListener;
    private ControllerListener mediaPlayerControllerListener;
    private Player mediaProcessor;
    private MediaStartAction mediaStartAction;
    private AbstractAction mediaStopAction;
    private BoundedRangeModel mediaTimeSliderModel;
    private OpenProjectAction openProjectAction;
    private URI projectSelected;
    private RoadSurveyWindow rsw;
    private WindowListener windowListener;


}
