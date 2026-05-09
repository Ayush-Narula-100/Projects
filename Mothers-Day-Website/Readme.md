# OurFlix • Mother's Day ❤️

A beautiful, emotional, and interactive single-page website created as a special tribute for **Mother's Day**.



## ✨ About This Project

This is a heartfelt digital gift featuring:
- Warm memories
- Special songs
- A cinematic hero section
- Smooth animations and modern design

Built with love to celebrate the most important person in our lives.

## 🌟 Features

- Fully responsive & mobile-friendly design
- Elegant glassmorphism cards with hover effects
- Interactive horizontal memory carousel
- "Our Songs" section with direct YouTube links
- Modal popup for memories and video playback
- Hero section with "Play Story" video button
- Smooth scrolling navigation
- Fixed transparent navbar
- ESC key support to close modal
- Tailwind CSS + Font Awesome

## 📁 Project Files
OurFlix-Mothers-Day/
├── index.html                 # Main website file
├── Images          # Background image + logo
├── test.mp4                   # Hero story video (optional)
└── README.md


## 🚀 How to Run

1. Download all the files.
2. Make sure Images is in the same folder as `index.html`.
3. (Optional) Add your `test.mp4` video for the hero section.
4. Double-click `index.html` to open it in your browser.

**No installation or server required.**

## 🎨 Customization Guide

### Changing Background Image
Replace `.png` with your preferred image.

### Adding / Editing Memories
Edit the cards inside the **Memories** section and update the `showContent()` function in the script.

### Adding More Songs
Add new anchor tags in the **Our Songs** section like this:

```html
<a href="YOUR_YOUTUBE_LINK" class="soft-card ...">
  <img src="YOUR_IMAGE.jpg" class="w-full">
  <div class="p-4">Song Name</div>
</a>