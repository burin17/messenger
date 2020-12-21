const profilesList = document.getElementById('profiles');
const searchBar = document.getElementById('searchBar');

searchBar.addEventListener('keyup', (e) => {
    console.log(loadProfiles(searchBar.value));
});

const loadProfiles = async (piece) => {
    try {
        const url = new URL('http://localhost:8080/api/profiles/piece'),
            params = {'piece':piece};
        Object.keys(params).forEach(key => url.searchParams.append(key, params[key]));
        const usernames = await fetch(url)
            .then(response => response.json());
        profilesList.innerHTML = usernames
            .map((username) => {
                return `<li class="list-group-item">${username}</li>`;
            }).join('');
    } catch (err) {
        console.log(err);
    }
};