using UnityEngine;
using System.Collections;

public class ExplosionSound : MonoBehaviour {

	[SerializeField]
	private AudioSource audioSource = null;
	[SerializeField]
	private AudioClip audio1 = null;
	[SerializeField]
	private AudioClip audio2 = null;
	[SerializeField]
	private AudioClip audio3 = null;

	void Start () {
		int ran = Random.Range(0,4);

		switch(ran)
		{
		case 0:
			audioSource.PlayOneShot(audio1);
			break;
		case 1:
			audioSource.PlayOneShot(audio1);
			break;
		case 2:
			audioSource.PlayOneShot(audio2);
			break;
		case 3:
			audioSource.PlayOneShot(audio3);
			break;
		case 4:
			audioSource.PlayOneShot(audio2);
			break;
		default:
			audioSource.PlayOneShot(audio3);
			break;
		}
	}

}
