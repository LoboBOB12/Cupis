package com.cupisbet.newbet.fragment;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cupisbet.newbet.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MatchFragment extends Fragment {

    private LinearLayout cardContainer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_match, container, false);
        cardContainer = view.findViewById(R.id.card_container);

        // Запуск задачи получения данных
        FetchEventsTask fetchEventsTask = new FetchEventsTask();
        fetchEventsTask.execute();

        return view;
    }

    private void displayEvents(List<Event> events) {
        cardContainer.removeAllViews();

        for (Event event : events) {
            View cardView = getLayoutInflater().inflate(R.layout.card_event, cardContainer, false);

            TextView sportNameTextView = cardView.findViewById(R.id.sport_name);
            TextView team1TextView = cardView.findViewById(R.id.team1);
            TextView team2TextView = cardView.findViewById(R.id.team2);
            TextView matchDateTextView = cardView.findViewById(R.id.match_date);

            sportNameTextView.setText(event.getSportName());
            team1TextView.setText(event.getTeam1());
            team2TextView.setText(event.getTeam2());
            matchDateTextView.setText(event.getMatchDate());

            cardContainer.addView(cardView);
        }
    }

    private class FetchEventsTask extends AsyncTask<Void, Void, List<Event>> {

        @Override
        protected List<Event> doInBackground(Void... voids) {
            List<Event> events = new ArrayList<>();

            try {
                URL url = new URL("https://apistoreapp.ru/api/events/get_events_data_4");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    JSONObject jsonResponse = new JSONObject(response.toString());
                    JSONArray eventsArray = jsonResponse.getJSONArray("events");

                    for (int i = 0; i < eventsArray.length(); i++) {
                        JSONObject eventObject = eventsArray.getJSONObject(i);

                        String sportName = eventObject.getString("sport_name");
                        String team1 = eventObject.getString("team_1");
                        String team2 = eventObject.getString("team_2");
                        String matchDate = eventObject.getString("match_date");

                        events.add(new Event(sportName, team1, team2, matchDate));
                    }
                }

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            return events;
        }

        @Override
        protected void onPostExecute(List<Event> events) {
            displayEvents(events);
        }
    }

    private class Event {
        private String sportName;
        private String team1;
        private String team2;
        private String matchDate;

        public Event(String sportName, String team1, String team2, String matchDate) {
            this.sportName = sportName;
            this.team1 = team1;
            this.team2 = team2;
            this.matchDate = matchDate;
        }

        public String getSportName() {
            return sportName;
        }

        public String getTeam1() {
            return team1;
        }

        public String getTeam2() {
            return team2;
        }

        public String getMatchDate() {
            return matchDate;
        }
    }
}