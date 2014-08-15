using UnityEngine;
using System.Collections;

public class GalaxyMovements : MonoBehaviour {

	void Update () {
		Vector3 r = new Vector3(Random.Range(0.5f,1.0f), Random.Range(0.5f,1.0f), Random.Range(0.5f,1.0f));
		transform.Rotate (r);
	}

}
