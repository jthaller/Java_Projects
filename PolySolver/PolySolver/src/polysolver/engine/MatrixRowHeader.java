package polysolver.engine;

class MatrixRowHeader {
	public Polyomino poly;
	public CoordOri coord;
	public MatrixCell first;
	public MatrixRowHeader up, down;

	public MatrixRowHeader(Polyomino p, CoordOri c){
		poly=p;
		if( c!=null) coord = new CoordOri(c);
		first = new MatrixCell(null,this);
		up=down=this;
	}
	public void place(Board b){
		if( poly!=null ) poly.placeS(coord);
		else b.setContentsUnsafe(coord.coord,-2);
	}
	public void remove(Board b){
		if( poly!=null ) poly.removeS();
		else b.setContentsUnsafe(coord.coord,0);
	}



	public void insert(MatrixRowHeader mh){
		mh.up=up;
		mh.down=this;
		up.down =mh;
		up = mh;
	}

}