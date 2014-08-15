using UnityEngine;
using System.Collections;
using System.Runtime.InteropServices;
using SharpConnect;
using System.Security.Permissions;

public class PlayManager : MonoBehaviour {

	[SerializeField]
	private GameObject GalaxyObject1 = null;
	[SerializeField]
	private TextMesh Text1 = null;
	private int GalaxyHP1 = 10000;

	[SerializeField]
	private GameObject GalaxyObject2 = null;
	[SerializeField]
	private TextMesh Text2 = null;
	private int GalaxyHP2 = 10000;

	[SerializeField]
	private GameObject GalaxyObject3 = null;
	[SerializeField]
	private TextMesh Text3 = null;
	private int GalaxyHP3 = 10000;

	[SerializeField]
	private GameObject GalaxyObject4 = null;
	[SerializeField]
	private TextMesh Text4 = null;
	private int GalaxyHP4 = 10000;


	[SerializeField]
	private GameObject ExplosionParticle = null;

	private bool isGamePlaying = false;

	public Connector test=new Connector();
	string lastMessage;

	void Start () 
	{
		Debug.Log(test.fnConnectResult("127.0.0.1",10000,System.Environment.MachineName));
		if (test.res !="")
		{
			Debug.Log(test.res);
		}
		isGamePlaying = true;
	}

	void Update ()
	{

		int explosionTarget = 0;
		int changedHP = 0;

		if (test.strMessage.Length > 0)
		{
			string message = test.strMessage;
			//String message = "#" + GID2 + "," + galaxyHP + "!";
			
			Debug.Log("message:" + message);

			char gid = message[0];
			Debug.Log("gid:" + gid);
			explosionTarget = int.Parse(gid.ToString());


			string HP = message.Substring(1);

			Debug.Log("HP:" + HP);
			changedHP = int.Parse(HP+"00");
			test.strMessage = "";


		}

		if(changedHP<101)
		{
			changedHP = 0;
			isGamePlaying = false;
			Debug.Log("!!!!!!!!");
		}

		switch(explosionTarget)
		{
		case 1:
			Instantiate(ExplosionParticle, GalaxyObject1.transform.position, GalaxyObject1.transform.rotation);
			GalaxyHP1 = changedHP;
			break;
		case 2:
			Instantiate(ExplosionParticle, GalaxyObject2.transform.position, GalaxyObject2.transform.rotation);
			GalaxyHP2 = changedHP;
			break;
		case 3:
			Instantiate(ExplosionParticle, GalaxyObject3.transform.position, GalaxyObject3.transform.rotation);
			GalaxyHP3 = changedHP;
			break;
		case 4:
			Instantiate(ExplosionParticle, GalaxyObject4.transform.position, GalaxyObject4.transform.rotation);
			GalaxyHP4 = changedHP;
			break;
		}



		Text1.text = GalaxyHP1.ToString();
		Text2.text = GalaxyHP2.ToString();
		Text3.text = GalaxyHP3.ToString();
		Text4.text = GalaxyHP4.ToString();
	}
}
