import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './location-data.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ILocationDataDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const LocationDataDetail = (props: ILocationDataDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { locationDataEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="locationDataDetailsHeading">LocationData</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{locationDataEntity.id}</dd>
          <dt>
            <span id="floor">Floor</span>
          </dt>
          <dd>{locationDataEntity.floor}</dd>
          <dt>
            <span id="room">Room</span>
          </dt>
          <dd>{locationDataEntity.room}</dd>
          <dt>
            <span id="additionalInfo">Additional Info</span>
          </dt>
          <dd>{locationDataEntity.additionalInfo}</dd>
        </dl>
        <Button tag={Link} to="/location-data" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/location-data/${locationDataEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ locationData }: IRootState) => ({
  locationDataEntity: locationData.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(LocationDataDetail);
