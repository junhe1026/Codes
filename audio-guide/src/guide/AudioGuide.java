package guide;

import location.Location;
import location.LocationAware;
import media.MediaPlayerControl;
import media.MediaPlayerListener;
import media.Track;

import java.util.Vector;


public class AudioGuide implements LocationAware, MediaPlayerListener {
	private final MediaLibrary mediaLibrary;
	private final MediaPlayerControl mediaPlayer;
    private boolean isPlayerOccupied = false;

    private Vector<Location> unplayedTracks = new Vector<Location>();
    private Vector<Location> visitedLocations = new Vector<Location>();

	public AudioGuide(MediaLibrary mediaLibrary, MediaPlayerControl mediaPlayer) {
		this.mediaLibrary = mediaLibrary;
		this.mediaPlayer = mediaPlayer;
	}

	@Override
	public void locationChanged(Location newLocation) {

		Track trackForNewLocation;
        //System.out.println("enter and busy is "+isPlayerOccupied);
        if (mediaLibrary.isExistTrackFor(newLocation)){
            //track exists, get track
            trackForNewLocation = mediaLibrary.trackFor(newLocation);

            if (!isLocationVisited(newLocation)){
                // track has not played before
                if (isPlayerOccupied == false){
                    // player is available, play immediately
                    mediaPlayer.play(trackForNewLocation);
                    isPlayerOccupied = true;
                    visitedLocations.add(newLocation);
                }
                else{
//                    boolean block = true;
//                    int tries = 0;
//                    while (block && tries < 50 ){
//                        try{
//                            // wait 6 seconds and check if mediaStatus has been changed
//                            java.util.concurrent.TimeUnit.SECONDS.sleep(6);
//                            if (isPlayerOccupied == false){
//                                block = false;
//                            }
//                            tries++;
//                            System.out.println("checking player "+ tries + " times");
//                        }
//                        catch (Exception e){
//                            System.out.println("Time out");
//                        }
//
//                    }
                    unplayedTracks.add(newLocation);
                }

            }

        }
        //System.out.println("out");
	}

    public boolean isLocationVisited(Location newLocation){
        for (int i = 0; i < visitedLocations.size();i++){
            Location visitedLocation = visitedLocations.elementAt(i);
            if (visitedLocation == newLocation){
                return true;
            }
        }
        return false;
    }


    @Override
    public void trackFinished() {
        if (unplayedTracks.size() == 0){
            isPlayerOccupied = false;
        }
        else{
            Location nextLocation = unplayedTracks.elementAt(0);
            unplayedTracks.remove(0);
            Track nextTrack = mediaLibrary.trackFor(nextLocation);
            mediaPlayer.play(nextTrack);
            isPlayerOccupied = true;
            visitedLocations.add(nextLocation);
        }

    }
}
