document.getElementById('btnLogin').addEventListener('click', async function(){
  const msg = document.getElementById('msg');
  msg.style.color = '#b00020';
  msg.textContent = 'Please wait...';

  const payload = {
    userName: document.getElementById('userName').value || null,
    password: document.getElementById('password').value || null
  };

  try {
    const res = await fetch('/proxy/users/login', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload)
    });
    const text = await res.text();
    if(res.ok){
      msg.style.color = 'green';
      msg.textContent = 'Login successful: ' + text;
    } else {
      msg.textContent = 'Login failed: ' + (text || ('Status ' + res.status));
    }
  } catch(err){
    msg.textContent = 'Network error: ' + err.message;
  }
});
