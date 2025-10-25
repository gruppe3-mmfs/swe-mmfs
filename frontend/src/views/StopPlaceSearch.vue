<script setup lang="ts">
import StopPlaceSearch from "../components/StopPlaceSearch.vue";
</script>

<template>
  <div>
    <h2>Search Stop Places</h2>
    <input v-model="query" placeholder="Enter stop name (e.g., Oslo S)" />
    <button @click="search">Search</button>
    <ul>
      <li v-for="stop in stopPlaces" :key="stop.id">
        {{ stop.name }} ({{ stop.id }}) - ({{ stop.latitude }},
        {{ stop.longitude }})
        <button @click="getDetails(stop.id)">Details</button>
      </li>
    </ul>
    <div v-if="selectedStop">
      <h3>{{ selectedStop.name }}</h3>
      <p>ID: {{ selectedStop.id }}</p>
      <p>
        Location: ({{ selectedStop.latitude }}, {{ selectedStop.longitude }})
      </p>
      <p>Description: {{ selectedStop.description || "N/A" }}</p>
    </div>
  </div>
</template>
