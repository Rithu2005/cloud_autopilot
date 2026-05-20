/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{js,jsx,ts,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        dark: '#050816',
        card: 'rgba(17, 25, 40, 0.75)',
        accent: {
          blue: '#3b82f6',
          pink: '#ec4899',
          red: '#ef4444',
          yellow: '#facc15',
        }
      }
    },
  },
  plugins: [],
};
