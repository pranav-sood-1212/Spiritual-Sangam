const BASE_URL = "http://localhost:8080/api";

export async function fetchAllBhajans() {
  const res = await fetch(`${BASE_URL}/Bhajan/getAllBhajans`);
  if (!res.ok) throw new Error("Failed to fetch bhajans");
  return res.json();
}

// export async function fetchYoutubeOnlyBhajans() {
//   const res = await fetch(`${BASE_URL}/bhajans/source/youtube`);
//   if (!res.ok) throw new Error("Failed to fetch YouTube bhajans");
//   return res.json();
// }

// export async function fetchLocalMusicianBhajans() {
//   const res = await fetch(`${BASE_URL}/bhajans/source/local`);
//   if (!res.ok) throw new Error("Failed to fetch local bhajans");
//   return res.json();
// }

export async function fetchBhajansByMood(mood: string) {
  const res = await fetch(`${BASE_URL}/Bhajan/getBhajansByMood/${mood}`);
  if (!res.ok) throw new Error(`Failed to fetch bhajans for mood: ${mood}`);
  return res.json();
}

export async function fetchBhajansByDeity(deity: string) {
  const res = await fetch(`${BASE_URL}/Bhajan/getBhajansByDeity/${deity}`);
  if (!res.ok) throw new Error(`Failed to fetch bhajans for deity: ${deity}`);
  return res.json();
}
