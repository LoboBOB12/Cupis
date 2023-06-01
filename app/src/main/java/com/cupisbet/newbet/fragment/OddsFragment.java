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

public class OddsFragment extends Fragment {

    private LinearLayout oddsContainer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_odds, container, false);
        oddsContainer = view.findViewById(R.id.odds_container);

        // Запуск задачи получения данных
        FetchOddsTask fetchOddsTask = new FetchOddsTask();
        fetchOddsTask.execute();

        return view;
    }

    private void displayOdds(List<Event> events) {
        oddsContainer.removeAllViews();

        for (Event event : events) {
            View eventView = getLayoutInflater().inflate(R.layout.item_event, oddsContainer, false);

            TextView sportNameTextView = eventView.findViewById(R.id.sport_name);
            TextView countryTextView = eventView.findViewById(R.id.country);
            TextView tournamentNameTextView = eventView.findViewById(R.id.tournament_name);
            TextView team1TextView = eventView.findViewById(R.id.team_1);
            TextView team2TextView = eventView.findViewById(R.id.team_2);
            TextView matchDateTextView = eventView.findViewById(R.id.match_date);
            TextView totalTextView = eventView.findViewById(R.id.total);
            TextView foraTextView = eventView.findViewById(R.id.fora);

            sportNameTextView.setText(event.getSportName());
            countryTextView.setText(event.getCountry());
            tournamentNameTextView.setText(event.getTournamentName());
            team1TextView.setText(event.getTeam1());
            team2TextView.setText(event.getTeam2());
            matchDateTextView.setText(event.getMatchDate());
            totalTextView.setText(event.getTotal());
            foraTextView.setText(event.getFora());

            oddsContainer.addView(eventView);
        }
    }

    private class FetchOddsTask extends AsyncTask<Void, Void, List<Event>> {

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
                        String country = eventObject.getString("country");
                        String tournamentName = eventObject.getString("tournament_name");
                        String team1 = eventObject.getString("team_1");
                        String team2 = eventObject.getString("team_2");
                        String matchDate = eventObject.getString("match_date");

                        String total = "";
                        JSONObject totalObject = eventObject.optJSONObject("total");
                        if (totalObject != null) {
                            total = "Total: " + totalObject.getString("@freetext") + " - " +
                                    totalObject.getString("@value") + " (" +
                                    totalObject.getString("@name1") + ": " +
                                    totalObject.getString("@odd1") + ", " +
                                    totalObject.getString("@name2") + ": " +
                                    totalObject.getString("@odd2") + ")";
                        }

                        String fora = "";
                        JSONObject foraObject = eventObject.optJSONObject("fora");
                        if (foraObject != null) {
                            fora = "Fora: " + foraObject.getString("@freetext") + " - " +
                                    foraObject.getString("@value") + " (" +
                                    foraObject.getString("@name1") + ": " +
                                    foraObject.getString("@odd1") + ", " +
                                    foraObject.getString("@name2") + ": " +
                                    foraObject.getString("@odd2") + ")";
                        }

                        events.add(new Event(sportName, country, tournamentName, team1, team2, matchDate, total, fora));
                    }
                }

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            return events;
        }

        @Override
        protected void onPostExecute(List<Event> events) {
            displayOdds(events);
        }
    }

    private class Event {
        private String sportName;
        private String country;
        private String tournamentName;
        private String team1;
        private String team2;
        private String matchDate;
        private String total;
        private String fora;

        public Event(String sportName, String country, String tournamentName, String team1, String team2, String matchDate, String total, String fora) {
            this.sportName = sportName;
            this.country = country;
            this.tournamentName = tournamentName;
            this.team1 = team1;
            this.team2 = team2;
            this.matchDate = matchDate;
            this.total = total;
            this.fora = fora;
        }

        public String getSportName() {
            return sportName;
        }

        public String getCountry() {
            return country;
        }

        public String getTournamentName() {
            return tournamentName;
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

        public String getTotal() {
            return total;
        }

        public String getFora() {
            return fora;
        }
    }
}