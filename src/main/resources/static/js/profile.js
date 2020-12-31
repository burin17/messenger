const complaintBtn = document.getElementById("complaint-btn");

const abc = async () => {
    const url = new URL('http://localhost:8080/api/profiles/complaint'),
        params = {'id':target};
    Object.keys(params).forEach(key => url.searchParams.append(key, params[key]));
    const resp = await fetch(url, {method: 'POST'})
        .then(response => response.json());
    if(resp)
        alert('Complaint successfully sent')
    else
        alert('Complaint already sent')
}

complaintBtn.addEventListener('click', abc)