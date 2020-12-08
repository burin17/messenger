const profilesList = document.getElementById('profiles');
const searchBar = document.getElementById('searchBar');

searchBar.addEventListener('keyup', (e) => {
    console.log(loadProfiles(searchBar.value));
});

const loadProfiles = async (piece) => {
    try {
        const profiles = await fetch('http://localhost:8080/api/profiles/' + piece)
            .then(response => response.json());
        profilesList.innerHTML = profiles
            .map((profile) => {
                return `<li class="list-group-item">${profile.username}</li>`;
            }).join('');
    } catch (err) {
        console.log(err);
    }
};