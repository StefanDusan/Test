package com.intpfy.verifiers.event.speaker;

final class SpeakerVerificationMessages {

    static final String CAMERA_ON = "Camera ON.";
    static final String CAMERA_OFF = "Camera OFF.";

    static final String HAND_RAISED = "Hand raised.";
    static final String HAND_DOWN = "Hand down.";

    static final String SOURCE_CHANNEL_UNMUTED = "Source channel unmuted.";

    static final String LANGUAGE_CHANNEL_MUTED = "Language channel muted.";
    static final String LANGUAGE_CHANNEL_UNMUTED = "Language channel unmuted.";

    static final String LANGUAGE_CHANNEL_VOLUME_LEVEL_NOT_CHANGING = "Language channel Volume level not changing.";

    static final String INTERPRETING_LANGUAGE_SELECTED = "'%s' language selected as Interpreting.";
    static final String NO_INTERPRETING_LANGUAGE_SELECTED = "No Interpreting language selected.";

    static final String SPEAKERS_VISIBLE = "'Speakers' visible.";
    static final String SPEAKERS_NOT_VISIBLE = "'Speakers' not visible.";

    static final String SPEAKERS_IS_OF_LOBBY_TYPE = "Speakers is of 'Lobby' type.";
    static final String SPEAKERS_IS_OF_PARTICIPANTS_TYPE = "Speakers is of 'Participants' type.";

    static final String LOBBY_SPEAKERS_COUNT = "Lobby speakers count equal to '%s'.";
    static final String LOBBY_ACTIONS_AVAILABLE = "Lobby actions available.";
    static final String SPEAKER_PRESENT_IN_LOBBY = "Speaker '%s' present in Lobby.";
    static final String SPEAKER_NOT_PRESENT_IN_LOBBY = "Speaker '%s' is not present in Lobby.";
    static final String SPEAKER_LOBBY_ACTIONS_AVAILABLE = "Lobby actions available for Speaker '%s'.";

    static final String SPEAKER_HAS_HOST_ROLE_IN_PARTICIPANTS = "Speaker '%s' has 'Host' role in 'Participants'.";

    static final String SPEAKER_PRESENT_IN_PARTICIPANTS = "Speaker '%s' present in 'Participants'.";
    static final String SPEAKER_NOT_PRESENT_IN_PARTICIPANTS = "Speaker '%s' not present in 'Participants'.";

    static final String SPEAKER_HAND_RAISED_IN_PARTICIPANTS = "Speaker '%s' hand Raised in 'Participants'.";
    static final String SPEAKER_HAND_DOWN_IN_PARTICIPANTS = "Speaker '%s' hand Down in 'Participants'.";

    static final String SPEAKER_ASKED_TO_STREAM_IN_PARTICIPANTS = "Speaker '%s' is asked to stream in 'Participants'.";

    static final String SPEAKER_STREAMING_IN_PARTICIPANTS = "Speaker '%s' streaming in 'Participants'.";
    static final String SPEAKER_NOT_STREAMING_IN_PARTICIPANTS = "Speaker '%s' not streaming in 'Participants'.";

    static final String SCREEN_SHARING_ENABLED = "Screen sharing enabled.";
    static final String SCREEN_SHARING_DISABLED = "Screen sharing disabled.";

    static final String STREAM_CONTROLS_DISABLED = "Stream controls disabled.";

    static final String VOLUME_LEVEL_MAX_FOR_SOURCE_CHANNEL = "Volume level Max for Source channel.";
    static final String VOLUME_LEVEL_MIN_FOR_SOURCE_CHANNEL = "Volume level Min for Source channel.";

    static final String VOLUME_LEVEL_MAX_FOR_LANGUAGE_CHANNEL = "Volume level Max for Language channel.";
    static final String VOLUME_LEVEL_MIN_FOR_LANGUAGE_CHANNEL = "Volume level Min for Language channel.";

    static final String UNREAD_PRIVATE_MESSAGES_PRESENT = "Unread Private messages present.";
    static final String UNREAD_PRIVATE_MESSAGES_NOT_PRESENT = "Unread Private messages not present.";

    static final String EVENT_CHAT_MESSAGE_HIDDEN = "Event chat message '%s' hidden.";

    static final String VOTE_TAB_OPENED = "'Vote' tab opened.";
    static final String VOTE_IN_PROGRESS = "Vote in progress.";

    static final String VOTE_QUESTION = "Vote question is '%s'.";
    static final String VOTE_ANSWER = "Vote answer '#%s' is '%s'.";

    static final String SINGLE_VOTE_ANSWER_SELECTED = "Single Vote answer '%s' selected.";
    static final String MULTIPLE_VOTE_ANSWER_SELECTED = "Multiple Vote answer '%s' selected.";

    static final String VOTE_ACCEPTED = "Vote accepted.";

    static final String STREAM_WITH_AUDIO_ONLY_GOING_ON_UI = "Stream with Audio only going on UI.";
    static final String STREAM_WITH_AUDIO_AND_VIDEO_GOING_ON_UI = "Stream with Audio and video going on UI.";

    static final String STREAM_WITH_SCREEN_SHARING_GOING_ON_UI = "Stream with Screen sharing going on UI.";
    static final String STREAM_WITH_SCREEN_SHARING_NOT_GOING_ON_UI = "Stream with Screen sharing not going on UI.";

    static final String AUDIO_PRESENT_IN_SOURCE_CHANNEL = "Audio present in Source channel.";
    static final String AUDIO_NOT_PRESENT_IN_SOURCE_CHANNEL = "Audio not present in Source channel.";

    static final String AUDIO_PRESENT_IN_LANGUAGE_CHANNEL = "Audio present in Language channel.";
    static final String AUDIO_NOT_PRESENT_IN_LANGUAGE_CHANNEL = "Audio not present in Language channel.";

    static final String SLOW_DOWN_DIALOG_WINDOW_CONTENT = "'Slow down' dialog window content.";

    private SpeakerVerificationMessages() {
    }
}
