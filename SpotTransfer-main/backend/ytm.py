from ytmusicapi import YTMusic
import ytmusicapi
from spotify import get_all_tracks, get_playlist_name


def get_video_ids(ytmusic,tracks):
    video_ids = []
    missed_tracks = {
        "count": 0,
        "tracks": []
    }
    for track in tracks:
        try :
            search_string = f"{track['name']} {track['artists'][0]}"
            video_id = ytmusic.search(search_string, filter="songs")[0]["videoId"]
            video_ids.append(video_id)
        except :
            print(f"{track['name']} {track['artists'][0]} not found on YouTube Music")
            missed_tracks["count"] += 1
            missed_tracks["tracks"].append(f"{track['name']} {track['artists'][0]}")
    print(f"Found {len(video_ids)} songs on YouTube Music")
    if len(video_ids) == 0:
        raise Exception("No songs found on YouTube Music")
    return video_ids, missed_tracks


def create_ytm_playlist(playlist_link, headers):
    ytmusicapi.setup(filepath="header_auth.json", headers_raw=headers)
    ytmusic = YTMusic("header_auth.json")
    tracks = get_all_tracks(playlist_link, "IN")
    name = get_playlist_name(playlist_link)
    video_ids, missed_tracks = get_video_ids(ytmusic, tracks)
    ytmusic.create_playlist(name, "", "PRIVATE", video_ids)
    return missed_tracks

