<script>
export default {
  data() {
    return {
      query: "",
      stopPlaces: [],
      selectedStop: null,
    };
  },
  methods: {
    async search() {
      try {
        const response = await fetch(
          `/api/stop-places/search?query=${encodeURIComponent(this.query)}`,
        );
        if (!response.ok) throw new Error("Failed to fetch stop places");
        this.stopPlaces = await response.json();
      } catch (error) {
        console.error("Error:", error);
      }
    },
    async getDetails(id) {
      try {
        const response = await fetch(`/api/stop-places/${id}`);
        if (!response.ok) throw new Error("Failed to fetch stop place details");
        this.selectedStop = await response.json();
      } catch (error) {
        console.error("Error:", error);
      }
    },
  },
};
</script>
