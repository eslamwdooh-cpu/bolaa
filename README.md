
# VOLA v3 - FULL - Launcher + Player + Mobile Remote + Plus + Profiles

## What's in v3 - الكاملة تماما
- Everything in v2: Live + VOD posters + Series Episodes + EPG + Favorites + Continue Watching + Search + Categories
- NEW Mobile Remote via WiFi:
  - TV starts RemoteService on port 8080
  - On TV: Home > تحكم من الموبايل -> shows IP + Pair Code
  - On Phone (same WiFi): open http://TV_IP:8080 -> remote page with Up/Down/Left/Right/OK/Back/Home/Play/Pause/Search
  - Phone does NOT consume a TV slot - same rule as Morph
- NEW Profiles: Family / Kids - Kids profile = safe content flag, separate favorites
- NEW Plus:
  - Free: NEON SPORT theme only
  - Plus: CINEMA + MINIMAL themes unlocked, 5 TVs, priority
  - Activate with code VOLA-PLUS-XXX (demo - in production verify on your server)
- Launcher: VOLA is HOME launcher

## Build APK
1. Android Studio > Open vola-android-v3
2. Wait Sync
3. Build > Build APK(s) > Build APK
4. Install on Sharp Android TV: Allow Unknown Sources

## Test Plan on Sharp
1. Setup > Enter Duplex: duplexiptv.info:80 / user / pass
2. Library: check Live count, Movies posters, Series episodes
3. Player: play live, play movie, check Continue Watching
4. Remote: Home > تحكم من الموبايل > open IP:8080 on phone on same WiFi
5. Profiles: Add Kids profile
6. Plus: Enter VOLA-PLUS-TEST > try CINEMA theme

## Notes
- VOLA does NOT provide channels - you bring your own Xtream subscription.
- For production Plus, replace local code check with server verification + InstaPay/PayPal.
