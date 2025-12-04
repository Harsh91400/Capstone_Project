document.getElementById('regForm').addEventListener('submit', async function(e){
  e.preventDefault();
  const msg = document.getElementById('msg');
  msg.style.color = '#b00020';
  msg.textContent = 'Please wait...';

  const payload = {
    firstName: document.getElementById('firstName').value || null,
    lastName: document.getElementById('lastName').value || null,
    userName: document.getElementById('userName').value || null,
    password: document.getElementById('password').value || null,
    mobileNo: document.getElementById('mobileNo').value || null,
    email: document.getElementById('email').value || null,
    dob: document.getElementById('dob').value || null,
    dateOfOpen: document.getElementById('dateOfOpen').value || null,
    accountType: document.getElementById('accountType').value || null,
    amount: parseFloat(document.getElementById('amount').value) || 0,
    cheqFacil: document.getElementById('cheqFacil').value || 'YES',
    address1: document.getElementById('address1').value || null,
    address2: document.getElementById('address2').value || null,
    city: document.getElementById('city').value || null,
    state: document.getElementById('state').value || null,
    country: document.getElementById('country').value || null,
    zipCode: document.getElementById('zipCode').value || null,
    status: document.getElementById('status').value || 'ACTIVE'
  };

  try {
    const res = await fetch('/proxy/users/add', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload)
    });
    const text = await res.text();
    if(res.ok){
      msg.style.color = 'green';
      msg.textContent = 'Registration successful.';
      document.getElementById('regForm').reset();
    } else {
      msg.textContent = 'Registration failed: ' + (text || ('Status ' + res.status));
    }
  } catch(err){
    msg.textContent = 'Network error: ' + err.message;
  }
});
