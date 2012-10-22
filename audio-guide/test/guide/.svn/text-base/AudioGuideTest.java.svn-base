package guide;

import static org.junit.Assert.fail;

import location.Location;
import media.MediaPlayerControl;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(JMock.class)
public class AudioGuideTest {
    Mockery context = new JUnit4Mockery();

	MediaPlayerControl mediaPlayer = context.mock(MediaPlayerControl.class);
	MediaLibrary mediaLibrary = context.mock(MediaLibrary.class);

    media.Track initialLocationTrack = context.mock(media.Track.class, "initialLocationTrack");
	
	AudioGuide guide = new AudioGuide(mediaLibrary, mediaPlayer);
    final Location initialLocation = new Location("initialLocation");

    @Test
	public void playsMediaTrackForInitialLocation() throws Exception {

        context.checking(new Expectations(){{
            allowing(mediaLibrary).isExistTrackFor(initialLocation);will(returnValue(true));
            allowing(mediaLibrary).trackFor(initialLocation);will(returnValue(initialLocationTrack));
            oneOf(mediaPlayer).play(initialLocationTrack);

        }});

		guide.locationChanged(initialLocation);
	}


    @Test
    public void testPlayNothing(){
        final Location locationWithNoTrack = new Location("noTrackLocation");
        context.checking(new Expectations(){{
            allowing(mediaLibrary).isExistTrackFor(locationWithNoTrack);will(returnValue(false));
            never(mediaPlayer).stop();
        }});
        guide.locationChanged(locationWithNoTrack);
    }

    @Test
    public void testTrackOnlyPlayOnce(){
        // play the initialLocationTrack and recorded
        context.checking(new Expectations(){{
            allowing(mediaLibrary).isExistTrackFor(initialLocation);will(returnValue(true));
            allowing(mediaLibrary).trackFor(initialLocation);will(returnValue(initialLocationTrack));
            exactly(1).of(mediaPlayer).play(initialLocationTrack);

        }});
        guide.locationChanged(initialLocation);

        // Once visited again, player will not play agian
        context.checking(new Expectations(){{
            allowing(mediaLibrary).trackFor(initialLocation);will(returnValue(initialLocationTrack));
            never(mediaPlayer).play(initialLocationTrack);

        }});
        guide.locationChanged(initialLocation);
    }

    @Test
    public void testTrackPlayDelayed(){
        final Location secondLocation = new Location("SecondLocation");
        final media.Track trackForSecondLocation = context.mock(media.Track.class,"SecondTrack");
        // change to first location and play immediately
        context.checking(new Expectations(){{
            allowing(mediaLibrary).isExistTrackFor(initialLocation);will(returnValue(true));
            allowing(mediaLibrary).trackFor(initialLocation);will(returnValue(initialLocationTrack));
            oneOf(mediaPlayer).play(initialLocationTrack);
        }});
        guide.locationChanged(initialLocation);

        // change to second location but first track not finished
        context.checking(new Expectations(){{
            allowing(mediaLibrary).isExistTrackFor(secondLocation);will(returnValue(true));
            allowing(mediaLibrary).trackFor(secondLocation);will(returnValue(trackForSecondLocation));
            never(mediaPlayer).play(trackForSecondLocation);
        }});
        guide.locationChanged(secondLocation);

        // first track finished and second track plays
        context.checking(new Expectations(){{
            oneOf(mediaPlayer).play(trackForSecondLocation);
        }});
        guide.trackFinished();

    }

    @Test
    public void testTrackPlayImmediately(){
        final Location secondLocation = new Location("SecondLocation");
        final media.Track trackForSecondLocation = context.mock(media.Track.class,"SecondTrack");

        // change to first location and track finish
        context.checking(new Expectations(){{
            allowing(mediaLibrary).isExistTrackFor(initialLocation);will(returnValue(true));
            allowing(mediaLibrary).trackFor(initialLocation);will(returnValue(initialLocationTrack));
            oneOf(mediaPlayer).play(initialLocationTrack);
        }});
        guide.locationChanged(initialLocation);
        guide.trackFinished();

        // change to second location and track play immediately
        context.checking(new Expectations(){{
            allowing(mediaLibrary).isExistTrackFor(secondLocation);will(returnValue(true));
            allowing(mediaLibrary).trackFor(secondLocation);will(returnValue(trackForSecondLocation));
            oneOf(mediaPlayer).play(trackForSecondLocation);
        }});
        guide.locationChanged(secondLocation);
    }

}
